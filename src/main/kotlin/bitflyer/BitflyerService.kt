package bitflyer

import common.Service
import csv.DepositHistory
import json.JsonExporter
import csv.TradeHistory
import csv.WithdrawHistory
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
object BitflyerService : Service {

    override suspend fun execute() {
        val depositRecords = BitflyerDownloader.downloadDepositRecords()
        JsonExporter.export(
            DepositHistory(
                name = "bitflyer_deposit_history",
                unsortedRows = depositRecords
            )
        )
        val withdrawRecords = BitflyerDownloader.downloadWithdrawRecords()
        JsonExporter.export(
            WithdrawHistory(
                name = "bitflyer_withdraw_history",
                unsortedRows = withdrawRecords
            )
        )
        val tradeRecords = BitflyerImporter.importTradeRecords()
        JsonExporter.export(
            TradeHistory(
                name = "bitflyer_trade_history",
                unsortedRows = tradeRecords
            )
        )
    }

}