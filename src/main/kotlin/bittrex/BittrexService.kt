package bittrex

import common.Service
import json.DepositHistory
import json.JsonExporter
import json.SpotTradeHistory
import json.WithdrawHistory
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
object BittrexService : Service {

    override suspend fun execute() {
//        val depositRecords = BittrexDownloader.downloadDepositRecords()
//        JsonExporter.export(
//            DepositHistory(
//                name = "bittrex_deposit_history",
//                records = depositRecords
//            )
//        )
//        val withdrawRecords = BittrexDownloader.downloadWithdrawRecords()
//        JsonExporter.export(
//            WithdrawHistory(
//                name = "bittrex_withdraw_history",
//                records = withdrawRecords
//            )
//        )
//        val tradeRecords = BittrexDownloader.downloadTradeHistory()
//        JsonExporter.export(
//            SpotTradeHistory(
//                name = "bittrex_trade_history",
//                records = tradeRecords
//            )
//        )
    }

}
