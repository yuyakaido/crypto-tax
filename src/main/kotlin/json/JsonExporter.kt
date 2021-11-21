package json

import common.RetrofitCreator
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.encodeToJsonElement

@ExperimentalSerializationApi
object JsonExporter : IO {

    fun export(file: ChartHistory) {
        export(
            jsonFile = file,
            jsonText = RetrofitCreator.getJson().encodeToJsonElement(file.unsortedRows).toString()
        )
    }

    fun export(file: DepositHistory) {
        export(
            jsonFile = file,
            jsonText = RetrofitCreator.getJson().encodeToJsonElement(file.sortedRows).toString()
        )
    }

    fun export(file: WithdrawHistory) {
        export(
            jsonFile = file,
            jsonText = RetrofitCreator.getJson().encodeToJsonElement(file.sortedRows).toString()
        )
    }

    fun export(file: DistributionHistory) {
        export(
            jsonFile = file,
            jsonText = RetrofitCreator.getJson().encodeToJsonElement(file.sortedRows).toString()
        )
    }

    fun export(file: TradeHistory) {
        export(
            jsonFile = file,
            jsonText = RetrofitCreator.getJson().encodeToJsonElement(file.sortedRows).toString()
        )
    }

}