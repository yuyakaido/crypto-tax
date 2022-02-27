package binance

import common.Service
import json.*
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
//        val coinFutureTradeRecords = BinanceDownloader.downloadCoinFutureTradeHistory()
//        JsonExporter.export(
//            TradeHistory(
//                name = "binance_coin_future_trade_history",
//                records = coinFutureTradeRecords
//            )
//        )
//        val coinFutureProfitLossRecords = BinanceDownloader.downloadCoinFutureProfitLossHistory()
//        JsonExporter.export(
//            ProfitLossHistory(
//                name = "binance_coin_future_profit_loss_history",
//                records = coinFutureProfitLossRecords
//            )
//        )
//        val distributionRecords = BinanceDownloader.downloadDistributionHistory()
//        JsonExporter.export(
//            DistributionHistory(
//                name = "binance_distribution_history",
//                records = distributionRecords
//            )
//        )
    }

}