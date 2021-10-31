package bybit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
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

    fun execute() {
        val bybitApiKey = System.getProperty("BYBIT_API_KEY")
        val bybitApiSecret = System.getProperty("BYBIT_API_SECRET")

        val json = Json {
            ignoreUnknownKeys = true
            serializersModule = serializersModuleOf(BigDecimalSerializer)
        }
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.bybit.com/")
            .addConverterFactory(json.asConverterFactory(MediaType.get("application/json")))
            .build()
        val client = retrofit.create(BybitHttpClient::class.java)

        runBlocking {
            val withdraw = client.getWithdrawRecords(generateQueries(bybitApiKey, bybitApiSecret))
            outputBybitWithdrawRecord(withdraw)
            val spotTrade = client.getSpotTradeHistory(generateQueries(bybitApiKey, bybitApiSecret))
            outputBybitSpotTradeHistory(spotTrade)
        }
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

    private fun outputBybitWithdrawRecord(
        response: WithdrawRecordResponse
    ) {
        outputWithdrawRecord(
            outputFileName = "bybit_withdraw_record",
            records = response.toWithdrawRecords()
        )
    }

    private fun outputBybitSpotTradeHistory(
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
