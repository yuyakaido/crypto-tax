package model

import csv.CsvRecord
import java.math.BigDecimal
import java.time.ZonedDateTime

data class DistributionRecord(
    val distributedAt: ZonedDateTime,
    val asset: Asset,
    val amount: BigDecimal
) : CsvRecord {
    override val csv: String = "$distributedAt,$asset,$amount"
}