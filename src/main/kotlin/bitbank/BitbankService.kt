package bitbank

import common.Service
import csv.CsvExporter
import csv.TradeHistory
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
object BitbankService : Service {

    override suspend fun execute() {
//        val tradeRecords = BitbankDownloader.downloadTradeHistory()
//        CsvExporter.export(
//            TradeHistory(
//                name = "bitbank_trade_history",
//                lines = tradeRecords
//            )
//        )
    }

}