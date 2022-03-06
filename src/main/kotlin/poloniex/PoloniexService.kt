package poloniex

import common.Service
import json.*
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
object PoloniexService : Service {

    override suspend fun execute() {
//        val depositWithdrawRecords = PoloniexDownloader.downloadDepositWithdrawRecords()
//        JsonExporter.export(
//            DepositHistory(
//                name = "poloniex_deposit_history",
//                records = depositWithdrawRecords.deposits.map { it.toDepositRecord() }
//            )
//        )
//        JsonExporter.export(
//            WithdrawHistory(
//                name = "poloniex_withdraw_history",
//                records = depositWithdrawRecords.withdrawals.map { it.toWithdrawRecord() }
//            )
//        )
//        JsonExporter.export(
//            DistributionHistory(
//                name = "poloniex_distribution_history",
//                records = depositWithdrawRecords.adjustments.map { it.toDistributionRecord() }
//            )
//        )
//        val tradeRecords = PoloniexDownloader.downloadTradeRecords()
//        JsonExporter.export(
//            SpotTradeHistory(
//                name = "poloniex_trade_history",
//                records = tradeRecords
//            )
//        )
    }

}
