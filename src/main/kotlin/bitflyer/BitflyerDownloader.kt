package bitflyer

import common.Downloader
import common.RetrofitCreator
import csv.CsvExporter
import csv.DepositHistory
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
object BitflyerDownloader : Downloader {

    private val apiKey = System.getProperty("BITFLYER_API_KEY")
    private val apiSecret = System.getProperty("BITFLYER_API_SECRET")
    private val client = RetrofitCreator
        .newInstance(
            baseUrl = "https://api.bitflyer.com/v1/",
            interceptors = listOf(BitflyerHttpInterceptor(apiKey, apiSecret))
        )
        .create(BitflyerHttpClient::class.java)

    override fun execute() {
        runBlocking {
            val deposit = client.getDepositHistory()
            exportDepositHistory(deposit)
        }
    }

    private fun exportDepositHistory(responses: List<DepositResponse>) {
        CsvExporter.export(
            DepositHistory(
                name = "bitflyer_deposit_history",
                lines = responses.map { it.toDepositRecord() }
            )
        )
    }

}