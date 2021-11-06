package bitflyer

import common.Downloader
import common.RetrofitCreator
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
object BitflyerDownloader : Downloader {

    private val apiKey = System.getProperty("BITFLYER_API_KEY")
    private val apiSecret = System.getProperty("BITFLYER_API_SECRET")
    private val client = RetrofitCreator
        .newInstance("https://api.bitflyer.com/v1/")
        .create(BitflyerHttpClient::class.java)

    override fun execute() {
        runBlocking {
            val response = client.getMarkets()
            println(response.body())
        }
    }

}