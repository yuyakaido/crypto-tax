package bittrex

import common.RetrofitCreator
import kotlinx.serialization.ExperimentalSerializationApi
import model.DepositRecord

@ExperimentalSerializationApi
object BittrexDownloader {

    private val apiKey = System.getProperty("BITTREX_API_KEY")
    private val apiSecret = System.getProperty("BITTREX_API_SECRET")
    private val client = RetrofitCreator
        .newInstance(
            baseUrl = "https://api.bittrex.com/v3/",
            interceptors = listOf(
                BittrexHttpInterceptor(
                    apiKey = apiKey,
                    apiSecret = apiSecret
                )
            )
        )
        .create(BittrexHttpClient::class.java)

    suspend fun downloadDepositRecords(): List<DepositRecord> {
        return client.getDepositHistory().map { it.toDepositRecord() }
    }

}