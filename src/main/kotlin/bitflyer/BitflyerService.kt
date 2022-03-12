package bitflyer

import common.Service
import json.*
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
object BitflyerService : Service {

    override suspend fun execute() {
//        val depositRecords = BitflyerDownloader.downloadDepositRecords()
//        JsonExporter.export(
//            DepositHistory(
//                name = "bitflyer_deposit_history",
//                records = depositRecords
//            )
//        )
//        val withdrawRecords = BitflyerDownloader.downloadWithdrawRecords()
//        JsonExporter.export(
//            WithdrawHistory(
//                name = "bitflyer_withdraw_history",
//                records = withdrawRecords
//            )
//        )
//        val distributionRecords = BitflyerImporter.importDistributionRecords()
//        JsonExporter.export(
//            DistributionHistory(
//                name = "bitflyer_distribution_history",
//                records = distributionRecords
//            )
//        )
//        val tradeRecords = BitflyerImporter.importTradeRecords()
//        JsonExporter.export(
//            SpotTradeHistory(
//                name = "bitflyer_trade_history",
//                records = tradeRecords
//            )
//        )
    }

}
