package bybit

import common.Service
import json.*
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
//        val exchangeRecords = BybitDownloader.downloadExchangeRecords()
//        JsonExporter.export(
//            ExchangeHistory(
//                name = "bybit_exchange_history",
//                records = exchangeRecords
//            )
//        )
//        val spotTradeRecords = BybitDownloader.downloadSpotTradeRecords()
//        JsonExporter.export(
//            SpotTradeHistory(
//                name = "bybit_spot_trade_history",
//                records = spotTradeRecords
//            )
//        )
//        val inverseTradeRecords = BybitDownloader.downloadInversePerpetualTradeRecords()
//        JsonExporter.export(
//            FutureTradeHistory(
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
//            FutureTradeHistory(
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
//        val defiMiningDistributionRecords = BybitImporter.importDefiMiningRecords()
//        JsonExporter.export(
//            DistributionHistory(
//                name = "bybit_defi_mining_distribution_history",
//                records = defiMiningDistributionRecords
//            )
//        )
//        val flexibleStakingDistributionRecords = BybitImporter.importFlexibleStakingRecords()
//        JsonExporter.export(
//            DistributionHistory(
//                name = "bybit_flexible_staking_distribution_history",
//                records = flexibleStakingDistributionRecords
//            )
//        )
    }

}