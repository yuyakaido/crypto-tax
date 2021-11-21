package json

import model.ChartRecord

data class ChartHistory(
    override val name: String,
    override val unsortedRows: List<ChartRecord>
) : JsonFile<ChartRecord> {
    override val sortedRows: List<ChartRecord> = unsortedRows.sortedByDescending { it.date }
}
