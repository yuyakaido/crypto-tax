package bitflyer

import common.Service
import json.DistributionHistory
import json.JsonExporter
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
object BitflyerService : Service {

    override suspend fun execute() {
//        val depositRecords = BitflyerDownloader.downloadDepositRecords()
//        JsonExporter.export(
//            DepositHistory(
//                name = "bitflyer_deposit_history",
//                unsortedRows = depositRecords
//            )
//        )
//        val withdrawRecords = BitflyerDownloader.downloadWithdrawRecords()
//        JsonExporter.export(
//            WithdrawHistory(
//                name = "bitflyer_withdraw_history",
//                unsortedRows = withdrawRecords
//            )
//        )
        val distributionRecords = BitflyerImporter.importDistributionRecords()
        distributionRecords.forEach { println(it) }
        JsonExporter.export(
            DistributionHistory(
                name = "bitflyer_distribution_history",
                unsortedRows = distributionRecords
            )
        )
//        val tradeRecords = BitflyerImporter.importTradeRecords()
//        JsonExporter.export(
//            TradeHistory(
//                name = "bitflyer_trade_history",
//                unsortedRows = tradeRecords
//            )
//        )
    }

}
