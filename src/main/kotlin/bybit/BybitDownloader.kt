package bybit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.serializersModuleOf
import misc.BigDecimalSerializer
import model.TradeHistory
import model.WithdrawRecord
import okhttp3.MediaType
import retrofit2.Retrofit
import java.io.File
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

@ExperimentalSerializationApi
object BybitDownloader {

    private val apiKey = System.getProperty("BYBIT_API_KEY")
    private val apiSecret = System.getProperty("BYBIT_API_SECRET")
    private val json = Json {
        ignoreUnknownKeys = true
        serializersModule = serializersModuleOf(BigDecimalSerializer)
    }
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.bybit.com/")
        .addConverterFactory(json.asConverterFactory(MediaType.get("application/json")))
        .build()
    private val client = retrofit.create(BybitHttpClient::class.java)

    fun execute() {
        runBlocking {
            val withdraw = client.getWithdrawRecords(generateQueries())
            outputWithdrawRecord(withdraw)
            val future = fetchFutureTradeHistory()
            outputFutureTradeHistory(future)
            val spotTrade = client.getSpotTradeHistory(generateQueries())
            outputSpotTradeHistory(spotTrade)
        }
    }

    private fun generateQueries(
        parameters: Map<String, String> = emptyMap()
    ): Map<String, String> {
        // The parameters must be ordered in alphabetical order.
        // https://bybit-exchange.github.io/docs/inverse/#t-authentication
        val queryMap = parameters.plus(
            mapOf(
                "api_key" to apiKey,
                "timestamp" to System.currentTimeMillis().toString()
            )
        ).toSortedMap()
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

    private suspend fun fetchFutureTradeHistory(): List<FutureTradeHistoryResponse> {
        println("Fetching bybit future trade history")
        val responses = mutableListOf<FutureTradeHistoryResponse>()
        var page = 1
        while (true) {
            println("Page = $page")
            val queries = generateQueries(
                parameters = mapOf(
                    "symbol" to "BTCUSD",
                    "page" to page++.toString()
                )
            )
            val future = client.getFutureTradeHistory(queries)
            responses.add(future)
            if (future.result.tradeList.size < 50) {
                break
            }
            delay(5000)
        }
        return responses
    }

    private fun outputWithdrawRecord(
        response: WithdrawRecordResponse
    ) {
        outputWithdrawRecord(
            outputFileName = "bybit_withdraw_record",
            records = response.toWithdrawRecords()
        )
    }

    private fun outputFutureTradeHistory(
        responses: List<FutureTradeHistoryResponse>
    ) {
        outputTradeHistory(
            outputFileName = "bybit_future_trade_history",
            histories = responses.flatMap { it.toTradeHistories() }
        )
    }

    private fun outputSpotTradeHistory(
        response: SpotTradeHistoryResponse
    ) {
        outputTradeHistory(
            outputFileName = "bybit_spot_trade_history",
            histories = response.toTradeHistories()
        )
    }

    private fun outputWithdrawRecord(
        outputFileName: String,
        records: List<WithdrawRecord>
    ) {
        val outputDirectory = File("${System.getProperty("user.dir")}/build/outputs")
        outputDirectory.mkdir()
        val outputFile = File("${outputDirectory.path}/$outputFileName.csv")
        outputFile.createNewFile()
        outputFile.bufferedWriter().apply {
            appendLine(WithdrawRecord.CSV_HEADER)
            records.forEach { history ->
                appendLine(history.toCSV())
            }
        }.close()
    }

    private fun outputTradeHistory(
        outputFileName: String,
        histories: List<TradeHistory>
    ) {
        val outputDirectory = File("${System.getProperty("user.dir")}/build/outputs")
        outputDirectory.mkdir()
        val outputFile = File("${outputDirectory.path}/$outputFileName.csv")
        outputFile.createNewFile()
        outputFile.bufferedWriter().apply {
            appendLine(TradeHistory.CSV_HEADER)
            histories.forEach { history ->
                appendLine(history.toCSV())
            }
        }.close()
    }

}
