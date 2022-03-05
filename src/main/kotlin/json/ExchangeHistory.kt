package json

import common.RetrofitCreator
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.encodeToJsonElement
import model.ExchangeRecord

@ExperimentalSerializationApi
data class ExchangeHistory(
    override val name: String,
    override val records: List<ExchangeRecord>
) : JsonFile<ExchangeRecord> {
    override val json: JsonElement = RetrofitCreator.getJson()
        .encodeToJsonElement(records.sortedByDescending { it.exchangedAt })
}
