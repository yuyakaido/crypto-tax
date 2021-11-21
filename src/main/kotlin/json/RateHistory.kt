package json

import common.RetrofitCreator
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.encodeToJsonElement
import model.RateRecord

@ExperimentalSerializationApi
data class RateHistory(
    override val name: String,
    override val records: List<RateRecord>
) : JsonFile<RateRecord> {
    override val json: JsonElement = RetrofitCreator.getJson().encodeToJsonElement(records)
}
