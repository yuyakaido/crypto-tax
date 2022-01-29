package binance

import common.Service
import json.DepositHistory
import json.JsonExporter
import json.WithdrawHistory
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
    }

}