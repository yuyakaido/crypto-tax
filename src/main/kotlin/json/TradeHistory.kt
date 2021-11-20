package json

import model.TradeRecord

data class TradeHistory(
    override val name: String,
    override val unsortedRows: List<TradeRecord>
) : JsonFile<TradeRecord> {
    override val sortedRows: List<TradeRecord> = unsortedRows.sortedByDescending { it.tradedAt }
}
