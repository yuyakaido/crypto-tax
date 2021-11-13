package csv

import model.TradeRecord

data class TradeHistory(
    override val name: String,
    override val unsortedRows: List<TradeRecord>
) : CsvFile {
    override val header: String = "TradedAt,Pair,Side,TradePrice,TradeAmount,FeeAmount,FeeAsset"
    override val sortedRows: List<CsvRecord> = unsortedRows.sortedByDescending { it.tradedAt }
}
