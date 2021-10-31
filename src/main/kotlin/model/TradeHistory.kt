package model

import java.math.BigDecimal
import java.time.ZonedDateTime

data class TradeHistory(
    val tradedAt: ZonedDateTime,
    val pair: Pair<Asset, Asset>,
    val side: Side,
    val price: BigDecimal,
    val qty: BigDecimal,
    val feeQty: BigDecimal,
    val feeAsset: Asset
) {
    companion object {
        const val CSV_HEADER = "TradedAt,Pair,model.Side,Price,Qty,FeeQty,FeeAsset"
    }
    fun toCSV(): String {
        return "$tradedAt,${pair.first}/${pair.second},$side,$price,$qty,$feeQty,$feeAsset"
    }
}
