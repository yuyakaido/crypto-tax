package bitbank

import common.Service
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
object BitbankService : Service {

    override suspend fun execute() {
//        val tradeRecords = BitbankDownloader.downloadTradeHistory()
//        JsonExporter.export(
//            TradeHistory(
//                name = "bitbank_trade_history",
//                unsortedRows = tradeRecords
//            )
//        )
    }

}
