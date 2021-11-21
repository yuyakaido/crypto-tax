package fx

import common.Service
import json.ChartHistory
import json.JsonExporter
import json.JsonImporter
import json.RateHistory
import kotlinx.serialization.ExperimentalSerializationApi
import model.ChartRecord

@ExperimentalSerializationApi
object ForeignExchangeService : Service {

    override suspend fun execute() {
//        val usdJpyRecords = ForeignExchangeImporter.import()
//        JsonExporter.export(
//            RateHistory(
//                name = "macrotrends_usd_jpy_chart_history",
//                records = usdJpyRecords
//            )
//        )
//        (2017..2021).forEach { year ->
//            val btcUsdtRecords = JsonImporter.importChartRecords(
//                name = "poloniex_btc_usdt_chart_history_$year"
//            )
//            val btcJpyRecords = btcUsdtRecords
//                .map { btcUsdRecord ->
//                    val usdJpyRecord = usdJpyRecords.first { it.date == btcUsdRecord.date.toLocalDate() }
//                    ChartRecord(
//                        date = btcUsdRecord.date,
//                        price = btcUsdRecord.price.multiply(usdJpyRecord.price)
//                    )
//                }
//            JsonExporter.export(
//                ChartHistory(
//                    name = "yuyakaido_btc_jpy_chart_history_$year",
//                    records = btcJpyRecords
//                )
//            )
//        }
    }

}