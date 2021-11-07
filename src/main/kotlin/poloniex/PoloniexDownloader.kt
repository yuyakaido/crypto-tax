package poloniex

import common.RetrofitCreator
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
object PoloniexDownloader {

    private val apiKey = System.getProperty("POLONIEX_API_KEY")
    private val apiSecret = System.getProperty("POLONIEX_API_SECRET")
    private val client = RetrofitCreator
        .newInstance(
            baseUrl = "https://poloniex.com/",
            interceptors = listOf(
                PoloniexHttpInterceptor(
                    apiKey = apiKey,
                    apiSecret = apiSecret
                )
            )
        )
        .create(PoloniexHttpClient::class.java)

    suspend fun downloadDepositWithdrawRecords(): DepositWithdrawHistoryResponse {
        return client.getDepositWithdrawHistory()
    }

}