package bitflyer

import common.RetrofitCreator
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
object BitflyerDownloader {

    private val apiKey = System.getProperty("BITFLYER_API_KEY")
    private val apiSecret = System.getProperty("BITFLYER_API_SECRET")

    val client: BitflyerHttpClient = RetrofitCreator
        .newInstance(
            baseUrl = "https://api.bitflyer.com/v1/",
            interceptors = listOf(BitflyerHttpInterceptor(apiKey, apiSecret))
        )
        .create(BitflyerHttpClient::class.java)

}