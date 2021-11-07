package bitflyer

import common.Service
import csv.CsvExporter
import csv.DepositHistory
import csv.TradeHistory
import csv.WithdrawHistory
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
object BitflyerService : Service {

    override suspend fun execute() {
//        val depositRecords = BitflyerDownloader.downloadDepositRecords()
//        CsvExporter.export(
//            DepositHistory(
//                name = "bitflyer_deposit_history",
//                lines = depositRecords
//            )
//        )
//        val withdrawRecords = BitflyerDownloader.downloadWithdrawRecords()
//        CsvExporter.export(
//            WithdrawHistory(
//                name = "bitfyer_withdraw_history",
//                lines = withdrawRecords
//            )
//        )
//        val tradeRecords = BitflyerImporter.importTradeRecords()
//        CsvExporter.export(
//            TradeHistory(
//                name = "bitflyer_trade_history",
//                lines = tradeRecords
//            )
//        )
    }

}