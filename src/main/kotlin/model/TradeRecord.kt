package model

import csv.ExportableAsCsvLine
import java.math.BigDecimal
import java.time.ZonedDateTime

data class TradeRecord(
    val tradedAt: ZonedDateTime,
    val pair: Pair<Asset, Asset>,
    val side: Side,
    val price: BigDecimal,
    val amount: BigDecimal,
    val feeQty: BigDecimal,
    val feeAsset: Asset
) : ExportableAsCsvLine {
    override val csv: String = "$tradedAt,${pair.first}/${pair.second},$side,$price,$amount,$feeQty,$feeAsset"
}
