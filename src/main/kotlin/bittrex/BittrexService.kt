package bittrex

import common.Service
import json.DepositHistory
import json.JsonExporter
import json.TradeHistory
import json.WithdrawHistory
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
object BittrexService : Service {

    override suspend fun execute() {
//        val depositRecords = BittrexDownloader.downloadDepositRecords()
//        JsonExporter.export(
//            DepositHistory(
//                name = "bittrex_deposit_history",
//                unsortedRows = depositRecords
//            )
//        )
//        val withdrawRecords = BittrexDownloader.downloadWithdrawRecords()
//        JsonExporter.export(
//            WithdrawHistory(
//                name = "bittrex_withdraw_history",
//                unsortedRows = withdrawRecords
//            )
//        )
//        val tradeRecords = BittrexDownloader.downloadTradeHistory()
//        JsonExporter.export(
//            TradeHistory(
//                name = "bittrex_trade_history",
//                unsortedRows = tradeRecords
//            )
//        )
    }

}
