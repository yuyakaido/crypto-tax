package bitbank

import common.Service
import json.JsonExporter
import json.SpotTradeHistory
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
object BitbankService : Service {

    override suspend fun execute() {
//        val tradeRecords = BitbankDownloader.downloadTradeHistory()
//        JsonExporter.export(
//            SpotTradeHistory(
//                name = "bitbank_trade_history",
//                records = tradeRecords
//            )
//        )
    }

}
