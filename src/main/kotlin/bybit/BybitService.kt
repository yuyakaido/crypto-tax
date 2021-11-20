package bybit

import common.Service
import json.JsonExporter
import csv.TradeHistory
import csv.WithdrawHistory
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
object BybitService : Service {

    override suspend fun execute() {
//        val withdrawRecords = BybitDownloader.downloadWithdrawRecords()
//        JsonExporter.export(
//            WithdrawHistory(
//                name = "bybit_withdraw_history",
//                unsortedRows = withdrawRecords
//            )
//        )
//        val inverseTradeRecords = BybitDownloader.downloadInversePerpetualTradeRecords()
//        JsonExporter.export(
//            TradeHistory(
//                name = "bybit_inverse_trade_history",
//                unsortedRows = inverseTradeRecords
//            )
//        )
//        val usdtTradeRecords = BybitDownloader.downloadUSDTPerpetualTradeRecords()
//        JsonExporter.export(
//            TradeHistory(
//                name = "bybit_usdt_trade_history",
//                unsortedRows = usdtTradeRecords
//            )
//        )
//        val spotTradeRecords = BybitDownloader.downloadSpotTradeRecords()
//        JsonExporter.export(
//            TradeHistory(
//                name = "bybit_spot_trade_history",
//                unsortedRows = spotTradeRecords
//            )
//        )
    }

}