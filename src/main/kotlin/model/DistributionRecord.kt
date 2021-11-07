package model

import csv.ExportableAsCsvLine
import java.math.BigDecimal
import java.time.ZonedDateTime

data class DistributionRecord(
    val distributedAt: ZonedDateTime,
    val asset: Asset,
    val amount: BigDecimal
) : ExportableAsCsvLine {
    override val csv: String = "$distributedAt,$asset,$amount"
}