import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.*
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.modules.serializersModuleOf
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.QueryMap
import java.io.File
import java.math.BigDecimal
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import kotlin.system.exitProcess

@ExperimentalSerializationApi
fun main() {
    println("Started!")

    val bybitApiKey = System.getProperty("BYBIT_API_KEY")
    val bybitApiSecret = System.getProperty("BYBIT_API_SECRET")
    println("BYBIT_API_KEY = $bybitApiKey")
    println("BYBIT_API_SECRET = $bybitApiSecret")

    val json = Json {
        ignoreUnknownKeys = true
        serializersModule = serializersModuleOf(BigDecimalSerializer)
    }
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.bybit.com/")
        .addConverterFactory(json.asConverterFactory(MediaType.get("application/json")))
        .build()
    val client = retrofit.create(HttpClient::class.java)

    runBlocking {
        val response = client.getSpotTradeHistory(generateQueries(bybitApiKey, bybitApiSecret))
        outputBybitSpotTradeHistory(response)
    }

    println("Completed!")

    exitProcess(0)
}

private fun generateQueries(
    apiKey: String,
    apiSecret: String,
    parameters: Map<String, String> = emptyMap()
): Map<String, String> {
    val queryMap = parameters.plus(
        mapOf(
            "api_key" to apiKey,
            "timestamp" to System.currentTimeMillis().toString()
        )
    )
    val queryString = buildString {
        val iterator = queryMap.iterator()
        while (iterator.hasNext()) {
            val entry = iterator.next()
            append("${entry.key}=${entry.value}")
            if (iterator.hasNext()) {
                append("&")
            }
        }
    }

    val algorithm = "HmacSHA256"
    val mac = Mac.getInstance(algorithm)
    mac.init(SecretKeySpec(apiSecret.toByteArray(), algorithm))
    val sign = mac.doFinal(queryString.toByteArray()).joinToString("") { String.format("%02x", it) }

    return queryMap.plus(
        "sign" to sign
    )
}

private fun outputBybitSpotTradeHistory(
    response: SpotTradeHistoryResponse
) {
    outputTradeHistory(
        outputFileName = "bybit_spot_trade_history",
        results = response.toTradeHistories()
    )
}

private fun outputTradeHistory(
    outputFileName: String,
    results: List<TradeHistory>
) {
    val outputDirectory = File("${System.getProperty("user.dir")}/build/outputs")
    outputDirectory.mkdir()
    val outputFile = File("${outputDirectory.path}/$outputFileName.csv")
    outputFile.createNewFile()
    outputFile.bufferedWriter().apply {
        appendLine(TradeHistory.CSV_HEADER)
        results.forEach { result ->
            appendLine(result.toCSV())
        }
    }.close()
}

enum class Side {
    Buy, Sell
}

enum class Asset {
    BTC,
    ETH,
    XRP,
    EOS,
    BIT,
    USDT;

    companion object {
        fun pair(symbol: String): Pair<Asset, Asset> {
            val first = values().first { symbol.startsWith(it.name) }
            val second = values().first { symbol.endsWith(it.name) }
            return first to second
        }
        fun single(asset: String): Asset {
            return values().first { asset == it.name }
        }
    }
}

data class TradeHistory(
    val tradedAt: ZonedDateTime,
    val pair: Pair<Asset, Asset>,
    val side: Side,
    val price: BigDecimal,
    val qty: BigDecimal,
    val feeQty: BigDecimal,
    val feeAsset: Asset
) {
    companion object {
        const val CSV_HEADER = "TradedAt,Pair,Side,Price,Qty,FeeQty,FeeAsset"
    }
    fun toCSV(): String {
        return "$tradedAt,${pair.first}/${pair.second},$side,$price,$qty,$feeQty,$feeAsset"
    }
}

interface HttpClient {
    @GET("/v2/private/exchange-order/list")
    suspend fun getAssetExchangeRecord(
        @QueryMap queries: Map<String, String>
    ): AssetExchangeRecordResponse

    @GET("/spot/v1/myTrades")
    suspend fun getSpotTradeHistory(
        @QueryMap queries: Map<String, String>
    ): SpotTradeHistoryResponse
}

@ExperimentalSerializationApi
@Serializer(forClass = BigDecimal::class)
object BigDecimalSerializer: KSerializer<BigDecimal> {
    override fun serialize(encoder: Encoder, value: BigDecimal) {
        val jsonEncoder = encoder as? JsonEncoder
        jsonEncoder?.encodeJsonElement(JsonPrimitive(value))
    }
    override fun deserialize(decoder: Decoder): BigDecimal {
        val jsonDecoder = decoder as? JsonDecoder
        return BigDecimal(jsonDecoder?.decodeJsonElement().toString())
    }
}

@Serializable
data class AssetExchangeRecordResponse(
    @SerialName("result") val results: List<Result>
) {
    @Serializable
    data class Result(
        @SerialName("id") val id: Long,
        @SerialName("from_coin") val fromCoin: String,
        @SerialName("to_coin") val toCoin: String,
        @SerialName("from_amount") @Contextual val fromAmount: BigDecimal,
        @SerialName("to_amount") @Contextual val toAmount: BigDecimal,
        @SerialName("exchange_rate") @Contextual val exchangeRate: BigDecimal,
        @SerialName("from_fee") @Contextual val fromFee: BigDecimal,
        @SerialName("created_at") val createdAt: String
    )
}

@Serializable
data class SpotTradeHistoryResponse(
    @SerialName("result") val results: List<Result>
) {
    @Serializable
    data class Result(
        @SerialName("id") val id: String,
        @SerialName("time") val time: String,
        @SerialName("symbol") val symbol: String,
        @SerialName("isBuyer") val isBuyer: Boolean,
        @SerialName("price") val price: String,
        @SerialName("qty") val qty: String,
        @SerialName("commission") val commission: String,
        @SerialName("commissionAsset") val commissionAsset: String
    )
    fun toTradeHistories(): List<TradeHistory> {
        return results
            .map { result ->
                TradeHistory(
                    tradedAt = ZonedDateTime.ofInstant(Instant.ofEpochMilli(result.time.toLong()), ZoneId.systemDefault()),
                    pair = Asset.pair(result.symbol),
                    side = if (result.isBuyer) { Side.Buy } else { Side.Sell },
                    price = BigDecimal(result.price),
                    qty = BigDecimal(result.qty),
                    feeQty = BigDecimal(result.commission),
                    feeAsset = Asset.single(result.commissionAsset)
                )
            }
    }
}
