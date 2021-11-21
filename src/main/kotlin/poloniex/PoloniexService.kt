package poloniex

import common.Service
import json.*
import kotlinx.serialization.ExperimentalSerializationApi
import java.time.LocalDateTime

@ExperimentalSerializationApi
object PoloniexService : Service {

    override suspend fun execute() {
//        val depositWithdrawRecords = PoloniexDownloader.downloadDepositWithdrawRecords()
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
//        JsonExporter.export(
//            DistributionHistory(
//                name = "poloniex_distribution_history",
//                unsortedRows = depositWithdrawRecords.adjustments.map { it.toDistributionRecord() }
//            )
//        )
//        val tradeRecords = PoloniexDownloader.downloadTradeRecords()
//        JsonExporter.export(
//            TradeHistory(
//                name = "poloniex_trade_history",
//                unsortedRows = tradeRecords
//            )
//        )
//        val from = LocalDateTime.of(2021, 11, 18, 0, 0 ,0)
//        val to = LocalDateTime.now()
//        val chartRecords = PoloniexDownloader.downloadChartRecords(from = from, to = to)
//        JsonExporter.export(
//            ChartHistory(
//                name = "btc_usdt_histrical_data",
//                unsortedRows = chartRecords
//            )
//        )
    }

}
