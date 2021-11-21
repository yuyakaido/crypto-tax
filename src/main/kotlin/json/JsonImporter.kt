package json

import common.RetrofitCreator
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import model.ChartRecord
import model.TradeRecord

@ExperimentalSerializationApi
object JsonImporter : IO {

    fun importChartRecords(name: String): List<ChartRecord> {
        return RetrofitCreator.getJson().decodeFromString(
            string = import(
                name = name,
                from = IO.Directory.Resources
            )
        )
    }

    fun importTradeRecords(name: String): List<TradeRecord> {
        return RetrofitCreator.getJson().decodeFromString(
            string = import(
                name = name,
                from = IO.Directory.Outputs
            )
        )
    }

}