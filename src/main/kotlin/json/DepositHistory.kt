package json

import common.RetrofitCreator
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.encodeToJsonElement
import model.DepositRecord

@ExperimentalSerializationApi
data class DepositHistory(
    override val name: String,
    override val records: List<DepositRecord>
) : JsonFile<DepositRecord> {
    override val json: JsonElement = RetrofitCreator.getJson()
        .encodeToJsonElement(records.sortedByDescending { it.depositedAt })
}
