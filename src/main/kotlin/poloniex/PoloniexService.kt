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
//        (2017..2021).forEach { year ->
//            val from = LocalDateTime.of(year, 1, 1, 0, 0 ,0)
//            val to = LocalDateTime.of(year, 12, 31, 23, 59, 59)
//            val chartRecords = PoloniexDownloader.downloadChartRecords(from = from, to = to)
//            JsonExporter.export(
//                ChartHistory(
//                    name = "poloniex_btc_usdt_chart_history_$year",
//                    unsortedRows = chartRecords
//                )
//            )
//        }
    }

}
