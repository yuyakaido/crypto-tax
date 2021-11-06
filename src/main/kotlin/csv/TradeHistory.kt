package csv

import model.TradeRecord

data class TradeHistory(
    override val name: String,
    override val lines: List<TradeRecord>
) : ExportableAsCsvFile {
    override val header: String = "TradedAt,Pair,Side,Price,Qty,FeeQty,FeeAsset"
}
