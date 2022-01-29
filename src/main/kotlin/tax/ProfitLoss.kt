package tax

import model.RecordType
import java.math.BigDecimal

data class ProfitLoss(
    val record: RecordType,
    val value: BigDecimal
)