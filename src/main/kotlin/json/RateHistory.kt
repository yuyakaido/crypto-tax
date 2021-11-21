package json

import model.RateRecord

data class RateHistory(
    override val name: String,
    override val unsortedRows: List<RateRecord>
) : JsonFile<RateRecord> {
    override val sortedRows: List<RateRecord> = unsortedRows.sortedByDescending { it.date }
}
