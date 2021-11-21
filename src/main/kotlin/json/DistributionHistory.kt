package json

import common.RetrofitCreator
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.encodeToJsonElement
import model.DistributionRecord

@ExperimentalSerializationApi
data class DistributionHistory(
    override val name: String,
    override val records: List<DistributionRecord>
) : JsonFile<DistributionRecord> {
    override val json: JsonElement = RetrofitCreator.getJson().encodeToJsonElement(records)
}
