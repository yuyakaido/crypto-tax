package bittrex

import common.Service
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
//            TradeHistory(
//                name = "bittrex_trade_history",
//                records = tradeRecords
//            )
//        )
    }

}
