package model

import csv.ExportableAsCsvLine
import java.math.BigDecimal
import java.time.ZonedDateTime

data class TradeRecord(
    val tradedAt: ZonedDateTime,
    val pair: Pair<Asset, Asset>,
    val side: Side,
    val tradePrice: BigDecimal,
    val tradeAmount: BigDecimal,
    val feeAmount: BigDecimal,
    val feeAsset: Asset
) : ExportableAsCsvLine {
    override val csv: String = "$tradedAt,${pair.first}/${pair.second},$side,$tradePrice,$tradeAmount,$feeAmount,$feeAsset"
}
