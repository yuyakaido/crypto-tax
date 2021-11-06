package bybit

import common.Downloader
import common.RetrofitCreator
import csv.CsvExporter
import csv.TradeHistory
import csv.WithdrawHistory
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
object BybitDownloader : Downloader {

    private val apiKey = System.getProperty("BYBIT_API_KEY")
    private val apiSecret = System.getProperty("BYBIT_API_SECRET")
    private val client = RetrofitCreator
        .newInstance("https://api.bybit.com/")
        .create(BybitHttpClient::class.java)

    override fun execute() {
        runBlocking {
//            val withdraw = client.getWithdrawHistory(generateQueries())
//            exportWithdrawHistory(withdraw)
//            val inverse = fetchInversePerpetualTradeHistory()
//            exportInversePerpetualTradeHistory(inverse)
//            val usdt = fetchUSDTPerpetualTradeHistory()
//            exportUSDTPerpetualTradeHistory(usdt)
//            val spot = fetchSpotTradeHistory()
//            exportSpotTradeHistory(spot)
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
        return queryMap.plus(
            "sign" to BybitSigner.generateSignature(apiKey, apiSecret, queryString)
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
            val response = client.getInversePerpetualTradeHistory(queries)
            responses.add(response)
            if (response.result.tradeList.size < 50) {
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
            val response = client.getUSDTPerpetualTradeHistory(queries)
            responses.add(response)
            if (response.result.data.size < 50) {
                break
            }
            delay(5000)
        }
        return responses
    }

    private suspend fun fetchSpotTradeHistory(): List<SpotTradeHistoryResponse> {
        println("Fetching bybit spot trade history")
        val responses = mutableListOf<SpotTradeHistoryResponse>()
        var page = 1
        while (true) {
            println("Page = $page")
            val queries = generateQueries(
                parameters = mapOf(
                    "symbol" to "DOTUSDT",
                    "page" to page++.toString()
                )
            )
            val response = client.getSpotTradeHistory(queries)
            responses.add(response)
            if (response.result.size < 50) {
                break
            }
            delay(5000)
        }
        return responses
    }

    private fun exportWithdrawHistory(
        response: WithdrawHistoryResponse
    ) {
        CsvExporter.export(
            WithdrawHistory(
                name = "bybit_withdraw_history",
                lines = response.toWithdrawRecords()
            )
        )
    }

    private fun exportInversePerpetualTradeHistory(
        responses: List<InversePerpetualTradeHistoryResponse>
    ) {
        CsvExporter.export(
            TradeHistory(
                name = "bybit_inverse_perpetual_trade_history",
                lines = responses.flatMap { it.toTradeRecords() }
            )
        )
    }

    private fun exportUSDTPerpetualTradeHistory(
        responses: List<USDTPerpetualTradeHistoryResponse>
    ) {
        CsvExporter.export(
            TradeHistory(
                name = "bybit_usdt_perpetual_trade_history",
                lines = responses.flatMap { it.toTradeRecords() }
            )
        )
    }

    private fun exportSpotTradeHistory(
        responses: List<SpotTradeHistoryResponse>
    ) {
        CsvExporter.export(
            TradeHistory(
                name = "bybit_spot_trade_history",
                lines = responses.flatMap { it.toTradeRecords() }
            )
        )
    }

}
