package binance

import common.Service
import json.JsonExporter
import json.TradeHistory
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
object BinanceService : Service {

    override suspend fun execute() {
//        val depositRecords = BinanceDownloader.downloadDepositHistory()
//        JsonExporter.export(
//            DepositHistory(
//                name = "binance_deposit_history",
//                records = depositRecords
//            )
//        )
//        val withdrawRecords = BinanceDownloader.downloadWithdrawHistory()
//        JsonExporter.export(
//            WithdrawHistory(
//                name = "binance_withdraw_history",
//                records = withdrawRecords
//            )
//        )
//        val spotTradeRecords = BinanceDownloader.downloadSpotTradeHistory()
//        JsonExporter.export(
//            TradeHistory(
//                name = "binance_spot_trade_history",
//                records = spotTradeRecords
//            )
//        )
    }

}