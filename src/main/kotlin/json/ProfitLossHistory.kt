package json

import common.RetrofitCreator
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.encodeToJsonElement
import model.ProfitLossRecord

@ExperimentalSerializationApi
data class ProfitLossHistory(
    override val name: String,
    override val records: List<ProfitLossRecord>
) : JsonFile<ProfitLossRecord> {
    override val json: JsonElement = RetrofitCreator.getJson()
        .encodeToJsonElement(records.sortedByDescending { it.tradedAt })
}