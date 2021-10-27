import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.*
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.*
import kotlinx.serialization.modules.serializersModuleOf
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap
import java.math.BigDecimal
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
        val response = client.getAssetExchangeRecord(
            queries = generateQueries(bybitApiKey, bybitApiSecret)
        )
        response.results.forEach { result ->
            println("${result.fromAmount}${result.fromCoin} to ${result.toAmount}${result.toCoin} (Rate: 1${result.fromCoin}=${result.exchangeRate}${result.toCoin} at ${result.createdAt})")
        }
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

interface HttpClient {
    @GET("/v2/public/funding/prev-funding-rate")
    suspend fun getPreviousFundingRate(
        @Query("symbol") symbol: String = "BTCUSD"
    ): PreviousFundingRateResponse

    @GET("/v2/private/account/api-key")
    suspend fun getApiKeyInfo(
        @QueryMap queries: Map<String, String>
    ): ApiKeyInfoResponse

    @GET("/v2/private/exchange-order/list")
    suspend fun getAssetExchangeRecord(
        @QueryMap queries: Map<String, String>
    ): AssetExchangeRecordResponse
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
data class PreviousFundingRateResponse(
    @SerialName("result") val result: Result
) {
    @Serializable
    data class Result(
        @SerialName("symbol") val symbol: String,
        @SerialName("funding_rate") val fundingRate: String,
        @SerialName("funding_rate_timestamp") val fundingRateTimestamp: Long
    )
}

@Serializable
data class ApiKeyInfoResponse(
    @SerialName("result") val results: List<Result>
) {
    @Serializable
    data class Result(
        @SerialName("api_key") val apiKey: String
    )
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