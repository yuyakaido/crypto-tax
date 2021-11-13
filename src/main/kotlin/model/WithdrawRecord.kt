package model

import csv.CsvRecord
import java.math.BigDecimal
import java.time.ZonedDateTime

data class WithdrawRecord(
    val withdrawnAt: ZonedDateTime,
    val asset: Asset,
    val amount: BigDecimal,
    val fee: BigDecimal
) : CsvRecord {
    override val csv: String = "$withdrawnAt,$asset,$amount,$fee"
}
