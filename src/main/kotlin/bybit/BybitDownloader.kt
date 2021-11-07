package bybit

import common.RetrofitCreator
import common.Signer
import kotlinx.coroutines.delay
import kotlinx.serialization.ExperimentalSerializationApi
import model.TradeRecord
import model.WithdrawRecord

@ExperimentalSerializationApi
object BybitDownloader {

    private val apiKey = System.getProperty("BYBIT_API_KEY")
    private val apiSecret = System.getProperty("BYBIT_API_SECRET")
    private val client = RetrofitCreator
        .newInstance("https://api.bybit.com/")
        .create(BybitHttpClient::class.java)

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
            "sign" to Signer.generateSignature(apiKey, apiSecret, queryString)
        )
    }

    suspend fun downloadWithdrawRecords(): List<WithdrawRecord> {
        return client.getWithdrawHistory(generateQueries()).toWithdrawRecords()
    }

    suspend fun downloadInversePerpetualTradeRecords(): List<TradeRecord> {
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
        return responses.flatMap { it.toTradeRecords() }
    }

    suspend fun downloadUSDTPerpetualTradeRecords(): List<TradeRecord> {
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
        return responses.flatMap { it.toTradeRecords() }
    }

    suspend fun downloadSpotTradeRecords(): List<TradeRecord> {
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
        return responses.flatMap { it.toTradeRecords() }
    }

}
