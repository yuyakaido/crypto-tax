package json

import common.RetrofitCreator
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.encodeToJsonElement
import model.WithdrawRecord

@ExperimentalSerializationApi
data class WithdrawHistory(
    override val name: String,
    override val records: List<WithdrawRecord>
) : JsonFile<WithdrawRecord> {
    override val json: JsonElement = RetrofitCreator.getJson()
        .encodeToJsonElement(records.sortedByDescending { it.withdrawnAt })
}
