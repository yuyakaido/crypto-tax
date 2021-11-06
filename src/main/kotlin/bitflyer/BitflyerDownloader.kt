package bitflyer

import common.Downloader
import common.RetrofitCreator
import csv.CsvExporter
import csv.FiatDepositHistory
import csv.FiatWithdrawHistory
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
            val deposit = client.getFiatDepositHistory()
            exportFiatDepositHistory(deposit)
            val withdraw = client.getFiatWithdrawHistory()
            exportFiatWithdrawHistory(withdraw)
        }
    }

    private fun exportFiatDepositHistory(responses: List<FiatDepositResponse>) {
        CsvExporter.export(
            FiatDepositHistory(
                name = "bitflyer_fiat_deposit_history",
                lines = responses.map { it.toFiatDepositRecord() }
            )
        )
    }

    private fun exportFiatWithdrawHistory(responses: List<FiatWithdrawResponse>) {
        CsvExporter.export(
            FiatWithdrawHistory(
                name = "bitflyer_fiat_withdraw_history",
                lines = responses.map { it.toFiatWithdrawRecord() }
            )
        )
    }

}