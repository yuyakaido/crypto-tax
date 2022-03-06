package json

import common.RetrofitCreator
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.encodeToJsonElement
import model.FutureTradeRecord

@ExperimentalSerializationApi
data class FutureTradeHistory(
    override val name: String,
    override val records: List<FutureTradeRecord>
) : JsonFile<FutureTradeRecord> {
    override val json: JsonElement = RetrofitCreator.getJson()
        .encodeToJsonElement(records.sortedByDescending { it.tradedAt })
}
