package bybit

import common.Downloader
import common.RetrofitCreator
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import model.TradeHistory
import model.WithdrawRecord
import java.io.File
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

@ExperimentalSerializationApi
object BybitDownloader : Downloader {

    private val apiKey = System.getProperty("BYBIT_API_KEY")
    private val apiSecret = System.getProperty("BYBIT_API_SECRET")
    private val client = RetrofitCreator
        .newInstance("https://api.bybit.com/")
        .create(BybitHttpClient::class.java)

    override fun execute() {
        runBlocking {
//            val withdraw = client.getWithdrawRecords(generateQueries())
//            outputWithdrawRecord(withdraw)
//            val inverse = fetchInversePerpetualTradeHistory()
//            outputInversePerpetualTradeHistory(inverse)
//            val usdt = fetchUSDTPerpetualTradeHistory()
//            outputUSDTPerpetualTradeHistory(usdt)
//            val spotTrade = client.getSpotTradeHistory(generateQueries())
//            outputSpotTradeHistory(spotTrade)
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

    private suspend fun fetchInversePerpetualTradeHistory(): List<InversePerpetualTradeHistoryResponse> {
        println("Fetching bybit inverse perpetual trade history")
        val responses = mutableListOf<InversePerpetualTradeHistoryResponse>()
        var page = 1
        while (true) {
            println("Page = $page")
            val queries = generateQueries(
                parameters = mapOf(
                    "symbol" to "BTCUSD",
                    "page" to page++.toString()
                )
            )
            val future = client.getInversePerpetualTradeHistory(queries)
            responses.add(future)
            if (future.result.tradeList.size < 50) {
                break
            }
            delay(5000)
        }
        return responses
    }

    private suspend fun fetchUSDTPerpetualTradeHistory(): List<USDTPerpetualTradeHistoryResponse> {
        println("Fetching bybit USDT perpetual trade history")
        val responses = mutableListOf<USDTPerpetualTradeHistoryResponse>()
        var page = 1
        while (true) {
            println("Page = $page")
            val queries = generateQueries(
                parameters = mapOf(
                    "symbol" to "BTCUSDT",
                    "page" to page++.toString()
                )
            )
            val future = client.getUSDTPerpetualTradeHistory(queries)
            responses.add(future)
            if (future.result.data.size < 50) {
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

    private fun outputInversePerpetualTradeHistory(
        responses: List<InversePerpetualTradeHistoryResponse>
    ) {
        outputTradeHistory(
            outputFileName = "bybit_inverse_perpetual_trade_history",
            histories = responses.flatMap { it.toTradeHistories() }
        )
    }

    private fun outputUSDTPerpetualTradeHistory(
        responses: List<USDTPerpetualTradeHistoryResponse>
    ) {
        outputTradeHistory(
            outputFileName = "bybit_usdt_perpetual_trade_history",
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
