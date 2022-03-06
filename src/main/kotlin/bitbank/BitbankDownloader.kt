package bitbank

import common.RetrofitCreator
import kotlinx.serialization.ExperimentalSerializationApi
import model.SpotTradeRecord

@ExperimentalSerializationApi
object BitbankDownloader {

    private val apiKey = System.getProperty("BITBANK_API_KEY")
    private val apiSecret = System.getProperty("BITBANK_API_SECRET")
    private val client = RetrofitCreator
        .newInstance(
            baseUrl = "https://api.bitbank.cc/v1/",
            interceptors = listOf(
                BitbankHttpInterceptor(
                    apiKey = apiKey,
                    apiSecret = apiSecret
                )
            )
        )
        .create(BitbankHttpClient::class.java)

    suspend fun downloadTradeHistory(): List<SpotTradeRecord> {
        return client.getTradeHistory().data.trades.map { it.toTradeRecord() }
    }

}