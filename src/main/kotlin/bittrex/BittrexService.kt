package bittrex

import common.Service
import csv.CsvExporter
import csv.DepositHistory
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
    }

}