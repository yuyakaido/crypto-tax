package json

import common.RetrofitCreator
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.encodeToJsonElement
import model.TradeRecord

@ExperimentalSerializationApi
data class TradeHistory(
    override val name: String,
    override val records: List<TradeRecord>
) : JsonFile<TradeRecord> {
    override val json: JsonElement = RetrofitCreator.getJson()
        .encodeToJsonElement(records.sortedByDescending { it.tradedAt })
}
