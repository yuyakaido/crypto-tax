package model

import java.math.BigDecimal
import java.time.ZonedDateTime

data class WithdrawRecord(
    val withdrawnAt: ZonedDateTime,
    val asset: Asset,
    val amount: BigDecimal,
    val fee: BigDecimal
) {
    companion object {
        const val CSV_HEADER = "WithdrawnAt,model.Asset,Amount,Fee"
    }
    fun toCSV(): String {
        return "$withdrawnAt,$asset,$amount,$fee"
    }
}
