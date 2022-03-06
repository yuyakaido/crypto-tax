package binance

import common.Service
import json.*
import kotlinx.serialization.ExperimentalSerializationApi
import model.Asset
import model.Symbol
import java.time.LocalDateTime

@ExperimentalSerializationApi
object BinanceService : Service {

    override suspend fun execute() {
//        (2017..2020).forEach { year ->
//            val symbol = Symbol.from(Asset.pair("BTC/USDT"))
//            val from = LocalDateTime.of(year, 1, 1, 0, 0 ,0)
//            val to = LocalDateTime.of(year, 12, 31, 23, 59, 59)
//            val chartRecords = BinanceDownloader.downloadChartRecords(symbol = symbol, from = from, to = to)
//            JsonExporter.export(
//                ChartHistory(
//                    name = "binance_btc_usdt_chart_history_$year",
//                    records = chartRecords
//                )
//            )
//        }
//        val symbols = listOf(
//            Symbol.from(Asset.pair("ETH/USDT")),
//            Symbol.from(Asset.pair("XRP/USDT")),
//            Symbol.from(Asset.pair("EOS/USDT")),
//            Symbol.from(Asset.pair("DOT/USDT")),
//        )
//        symbols.forEach { symbol ->
//            (2021..2021).forEach { year ->
//                val from = LocalDateTime.of(year, 1, 1, 0, 0 ,0)
//                val to = LocalDateTime.of(year, 12, 31, 23, 59, 59)
//                val chartRecords = BinanceDownloader.downloadChartRecords(symbol = symbol, from = from, to = to)
//                JsonExporter.export(
//                    ChartHistory(
//                        name = "binance_${symbol.first.value.lowercase()}_usdt_chart_history_$year",
//                        records = chartRecords
//                    )
//                )
//            }
//        }
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
//            FutureTradeHistory(
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