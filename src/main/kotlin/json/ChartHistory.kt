package json

import common.RetrofitCreator
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.encodeToJsonElement
import model.ChartRecord

@ExperimentalSerializationApi
data class ChartHistory(
    override val name: String,
    override val records: List<ChartRecord>
) : JsonFile<ChartRecord> {
    override val json: JsonElement = RetrofitCreator.getJson().encodeToJsonElement(records)
}
