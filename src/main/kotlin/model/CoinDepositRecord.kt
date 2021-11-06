package model

import csv.ExportableAsCsvLine
import java.math.BigDecimal
import java.time.ZonedDateTime

data class CoinDepositRecord(
    val depositedAt: ZonedDateTime,
    val asset: Asset,
    val amount: BigDecimal
) : ExportableAsCsvLine {
    override val csv: String = "$depositedAt,$asset,$amount"
}
