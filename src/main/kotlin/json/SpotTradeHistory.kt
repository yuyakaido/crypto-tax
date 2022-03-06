package json

import common.RetrofitCreator
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.encodeToJsonElement
import model.SpotTradeRecord

@ExperimentalSerializationApi
data class SpotTradeHistory(
    override val name: String,
    override val records: List<SpotTradeRecord>
) : JsonFile<SpotTradeRecord> {
    override val json: JsonElement = RetrofitCreator.getJson()
        .encodeToJsonElement(records.sortedByDescending { it.tradedAt })
}
