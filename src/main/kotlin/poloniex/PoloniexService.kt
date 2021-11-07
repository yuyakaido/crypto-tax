package poloniex

import common.Service
import csv.CsvExporter
import csv.DepositHistory
import csv.DistributionHistory
import csv.WithdrawHistory
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
object PoloniexService : Service {

    override suspend fun execute() {
        val response = PoloniexDownloader.downloadDepositWithdrawRecords()
        CsvExporter.export(
            exportable = DistributionHistory(
                name = "poloniex_distribution_history",
                lines = response.adjustments.map { it.toDistributionRecord() }
            )
        )
        CsvExporter.export(
            exportable = DepositHistory(
                name = "poloniex_deposit_history",
                lines = response.deposits.map { it.toDepositRecord() }
            )
        )
        CsvExporter.export(
            exportable = WithdrawHistory(
                name = "poloniex_withdraw_history",
                lines = response.withdrawals.map { it.toWithdrawRecord() }
            )
        )
    }

}