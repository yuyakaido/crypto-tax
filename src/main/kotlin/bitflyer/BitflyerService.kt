package bitflyer

import common.Service
import csv.*
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
object BitflyerService : Service {

    override suspend fun execute() {
//        val depositRecords = BitflyerDownloader.downloadDepositRecords()
//        CsvExporter.export(
//            DepositHistory(
//                name = "bitflyer_deposit_history",
//                unsortedRows = depositRecords
//            )
//        )
//        val withdrawRecords = BitflyerDownloader.downloadWithdrawRecords()
//        CsvExporter.export(
//            WithdrawHistory(
//                name = "bitflyer_withdraw_history",
//                unsortedRows = withdrawRecords
//            )
//        )
        val tradeRecords = BitflyerImporter.importTradeRecords()
        JsonExporter.export(
            TradeHistory(
                name = "bitflyer_trade_history",
                unsortedRows = tradeRecords
            )
        )
    }

}