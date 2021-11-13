package bybit

import common.RetrofitCreator
import kotlinx.coroutines.delay
import kotlinx.serialization.ExperimentalSerializationApi
import model.TradeRecord
import model.WithdrawRecord

@ExperimentalSerializationApi
object BybitDownloader {

    private val apiKey = System.getProperty("BYBIT_API_KEY")
    private val apiSecret = System.getProperty("BYBIT_API_SECRET")
    private val client = RetrofitCreator
        .newInstance(
            baseUrl = "https://api.bybit.com/",
            interceptors = listOf(
                BybitHttpInterceptor(
                    apiKey = apiKey,
                    apiSecret = apiSecret
                )
            )
        )
        .create(BybitHttpClient::class.java)

    suspend fun downloadWithdrawRecords(): List<WithdrawRecord> {
        return client.getWithdrawHistory().toWithdrawRecords()
    }

    suspend fun downloadInversePerpetualTradeRecords(): List<TradeRecord> {
        println("Fetching bybit inverse perpetual trade history")
        val responses = mutableListOf<InversePerpetualTradeHistoryResponse>()
        var page = 1
        while (true) {
            println("Page = $page")
            val response = client.getInversePerpetualTradeHistory(page = page++)
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
            val response = client.getUSDTPerpetualTradeHistory(page = page++)
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
            val response = client.getSpotTradeHistory(page = page++)
            responses.add(response)
            if (response.result.size < 50) {
                break
            }
            delay(5000)
        }
        return responses.flatMap { it.toTradeRecords() }
    }

}
