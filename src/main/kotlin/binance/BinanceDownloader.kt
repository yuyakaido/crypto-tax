package binance

import common.RetrofitCreator
import kotlinx.serialization.ExperimentalSerializationApi
import model.DepositRecord
import model.WithdrawRecord

@ExperimentalSerializationApi
object BinanceDownloader {

    private val apiKey = System.getProperty("BINANCE_API_KEY")
    private val apiSecret = System.getProperty("BINANCE_API_SECRET")
    private val client = RetrofitCreator
        .newInstance(
            baseUrl = "https://api.binance.com/",
            interceptors = listOf(
                BinanceHttpInterceptor(
                    apiKey = apiKey,
                    apiSecret = apiSecret
                )
            )
        )
        .create(BinanceHttpClient::class.java)

    suspend fun downloadDepositHistory(): List<DepositRecord> {
        return client.getDepositHistory().map { it.toDepositRecord() }
    }

    suspend fun downloadWithdrawHistory(): List<WithdrawRecord> {
        return client.getWithdrawHistory().map { it.toWithdrawRecord() }
    }

}