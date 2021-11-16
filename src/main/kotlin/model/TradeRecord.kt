package model

import csv.CsvRecord
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.time.ZonedDateTime

@Serializable
data class TradeRecord(
    @Contextual val tradedAt: ZonedDateTime,
    @Contextual val symbol: Symbol,
    @Contextual val side: Side,
    @Contextual val tradePrice: BigDecimal,
    @Contextual val tradeAmount: BigDecimal,
    @Contextual val feeAmount: BigDecimal,
    @Contextual val feeAsset: Asset
) : CsvRecord {
    override val csv: String = "$tradedAt,${symbol.first}/${symbol.second},$side,$tradePrice,$tradeAmount,$feeAmount,$feeAsset"
}
