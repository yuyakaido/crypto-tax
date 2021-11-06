package model

import csv.ExportableAsCsvLine
import java.math.BigDecimal
import java.time.ZonedDateTime

data class WithdrawRecord(
    val withdrawnAt: ZonedDateTime,
    val asset: Asset,
    val amount: BigDecimal,
    val fee: BigDecimal
) : ExportableAsCsvLine {
    override val csv: String = "$withdrawnAt,$asset,$amount,$fee"
}
