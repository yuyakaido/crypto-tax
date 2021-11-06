package bitflyer

import common.Downloader
import common.RetrofitCreator
import csv.CsvExporter
import csv.DepositHistory
import csv.WithdrawHistory
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
            val fiatDeposit = client.getFiatDepositHistory()
            exportFiatDepositHistory(fiatDeposit)
            val fiatWithdraw = client.getFiatWithdrawHistory()
            exportFiatWithdrawHistory(fiatWithdraw)
            val coinDeposit = client.getCoinDepositHistory()
            exportCoinDepositHistory(coinDeposit)
            val coinWithdraw = client.getCoinWithdrawHistory()
            exportCoinWithdrawHistory(coinWithdraw)
        }
    }

    private fun exportFiatDepositHistory(responses: List<FiatDepositResponse>) {
        CsvExporter.export(
            DepositHistory(
                name = "bitflyer_fiat_deposit_history",
                lines = responses.map { it.toDepositRecord() }
            )
        )
    }

    private fun exportFiatWithdrawHistory(responses: List<FiatWithdrawResponse>) {
        CsvExporter.export(
            WithdrawHistory(
                name = "bitflyer_fiat_withdraw_history",
                lines = responses.map { it.toWithdrawRecord() }
            )
        )
    }

    private fun exportCoinDepositHistory(responses: List<CoinDepositResponse>) {
        CsvExporter.export(
            DepositHistory(
                name = "bitflyer_coin_deposit_history",
                lines = responses.map { it.toDepositRecord() }
            )
        )
    }

    private fun exportCoinWithdrawHistory(responses: List<CoinWithdrawResponse>) {
        CsvExporter.export(
            WithdrawHistory(
                name = "bitflyer_coin_withdraw_history",
                lines = responses.map { it.toWithdrawRecord() }
            )
        )
    }

}