package bittrex

import common.Service
import csv.CsvExporter
import csv.DepositHistory
import csv.WithdrawHistory
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
object BittrexService : Service {

    override suspend fun execute() {
        val depositRecords = BittrexDownloader.downloadDepositRecords()
        CsvExporter.export(
            DepositHistory(
                name = "bittrex_deposit_history",
                lines = depositRecords
            )
        )
        val withdrawRecords = BittrexDownloader.downloadWithdrawRecords()
        CsvExporter.export(
            WithdrawHistory(
                name = "bittrex_withdraw_history",
                lines = withdrawRecords
            )
        )
    }

}
