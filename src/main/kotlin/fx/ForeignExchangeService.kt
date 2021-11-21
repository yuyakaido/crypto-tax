package fx

import common.Service
import json.JsonExporter
import json.RateHistory
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
object ForeignExchangeService : Service {

    override suspend fun execute() {
//        val records = ForeignExchangeImporter.import().filter { it.date.year >= 2016 }
//        JsonExporter.export(
//            RateHistory(
//                name = "macrotrends_usd_jpy_chart_history",
//                unsortedRows = records
//            )
//        )
    }

}