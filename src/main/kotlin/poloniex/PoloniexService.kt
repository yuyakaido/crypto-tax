package poloniex

import common.Service
import csv.*
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
object PoloniexService : Service {

    override suspend fun execute() {
//        val depositWithdrawRecords = PoloniexDownloader.downloadDepositWithdrawRecords()
//        CsvExporter.export(
//            DistributionHistory(
//                name = "poloniex_distribution_history",
//                lines = depositWithdrawRecords.adjustments.map { it.toDistributionRecord() }
//            )
//        )
//        CsvExporter.export(
//            DepositHistory(
//                name = "poloniex_deposit_history",
//                lines = depositWithdrawRecords.deposits.map { it.toDepositRecord() }
//            )
//        )
//        CsvExporter.export(
//            WithdrawHistory(
//                name = "poloniex_withdraw_history",
//                lines = depositWithdrawRecords.withdrawals.map { it.toWithdrawRecord() }
//            )
//        )
//        val tradeRecords = PoloniexDownloader.downloadTradeRecords()
//        CsvExporter.export(
//            TradeHistory(
//                name = "poloniex_trade_history",
//                lines = tradeRecords
//            )
//        )
    }

}