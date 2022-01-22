package bybit

import common.Service
import json.JsonExporter
import json.JsonImporter
import json.TradeHistory
import json.WithdrawHistory
import kotlinx.serialization.ExperimentalSerializationApi
import model.Asset
import model.Symbol

@ExperimentalSerializationApi
object BybitService : Service {

    override suspend fun execute() {
//        val withdrawRecords = BybitDownloader.downloadWithdrawRecords()
//        JsonExporter.export(
//            WithdrawHistory(
//                name = "bybit_withdraw_history",
//                records = withdrawRecords
//            )
//        )
//        val inverseTradeRecords = BybitDownloader.downloadInversePerpetualTradeRecords()
//        JsonExporter.export(
//            TradeHistory(
//                name = "bybit_inverse_trade_history",
//                records = inverseTradeRecords
//            )
//        )
//        val usdtTradeRecords = BybitDownloader.downloadUSDTPerpetualTradeRecords()
//        JsonExporter.export(
//            TradeHistory(
//                name = "bybit_usdt_trade_history",
//                records = usdtTradeRecords
//            )
//        )
//        val spotSymbols = BybitDownloader.downloadSpotSymbols()
//        val spotTradeRecords = BybitDownloader.downloadSpotTradeRecords(
//            symbols = spotSymbols
//        )
//        JsonExporter.export(
//            TradeHistory(
//                name = "bybit_spot_trade_history",
//                records = spotTradeRecords
//            )
//        )
    }

}