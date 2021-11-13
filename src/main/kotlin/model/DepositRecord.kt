package model

import csv.CsvRecord
import java.math.BigDecimal
import java.time.ZonedDateTime

data class DepositRecord(
    val depositedAt: ZonedDateTime,
    val asset: Asset,
    val amount: BigDecimal
) : CsvRecord {
    override val csv: String = "$depositedAt,$asset,$amount"
}
