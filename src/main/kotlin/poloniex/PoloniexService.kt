package poloniex

import common.Service
import csv.*
import json.JsonExporter
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
object PoloniexService : Service {

    override suspend fun execute() {
//        val depositWithdrawRecords = PoloniexDownloader.downloadDepositWithdrawRecords()
//        JsonExporter.export(
//            DistributionHistory(
//                name = "poloniex_distribution_history",
//                unsortedRows = depositWithdrawRecords.adjustments.map { it.toDistributionRecord() }
//            )
//        )
//        JsonExporter.export(
//            DepositHistory(
//                name = "poloniex_deposit_history",
//                unsortedRows = depositWithdrawRecords.deposits.map { it.toDepositRecord() }
//            )
//        )
//        JsonExporter.export(
//            WithdrawHistory(
//                name = "poloniex_withdraw_history",
//                unsortedRows = depositWithdrawRecords.withdrawals.map { it.toWithdrawRecord() }
//            )
//        )
//        val tradeRecords = PoloniexDownloader.downloadTradeRecords()
//        JsonExporter.export(
//            TradeHistory(
//                name = "poloniex_trade_history",
//                unsortedRows = tradeRecords
//            )
//        )
    }

}
