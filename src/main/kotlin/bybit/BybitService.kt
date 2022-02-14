package bybit

import common.Service
import json.JsonExporter
import json.ProfitLossHistory
import json.TradeHistory
import json.WithdrawHistory
import kotlinx.serialization.ExperimentalSerializationApi

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
//        val exchangeTradeRecords = BybitDownloader.downloadExchangeTradeRecords()
//        JsonExporter.export(
//            TradeHistory(
//                name = "bybit_exchange_trade_history",
//                records = exchangeTradeRecords
//            )
//        )
//        val spotTradeRecords = BybitDownloader.downloadSpotTradeRecords()
//        JsonExporter.export(
//            TradeHistory(
//                name = "bybit_spot_trade_history",
//                records = spotTradeRecords
//            )
//        )
//        val inverseTradeRecords = BybitDownloader.downloadInversePerpetualTradeRecords()
//        JsonExporter.export(
//            TradeHistory(
//                name = "bybit_inverse_trade_history",
//                records = inverseTradeRecords
//            )
//        )
//        val inverseProfitLossRecords = BybitDownloader.downloadInversePerpetualProfitLossRecords()
//        JsonExporter.export(
//            ProfitLossHistory(
//                name = "bybit_inverse_profit_loss_history",
//                records = inverseProfitLossRecords
//            )
//        )
//        val usdtTradeRecords = BybitDownloader.downloadUSDTPerpetualTradeRecords()
//        JsonExporter.export(
//            TradeHistory(
//                name = "bybit_usdt_trade_history",
//                records = usdtTradeRecords
//            )
//        )
//        val usdtProfitLossRecords = BybitDownloader.downloadUSDTPerpetualProfitLossRecords()
//        JsonExporter.export(
//            ProfitLossHistory(
//                name = "bybit_usdt_profit_loss_history",
//                records = usdtProfitLossRecords
//            )
//        )
    }

}