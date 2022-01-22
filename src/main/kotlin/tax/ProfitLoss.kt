package tax

import model.Symbol
import java.math.BigDecimal
import java.time.ZonedDateTime

data class ProfitLoss(
    val tradedAt: ZonedDateTime,
    val symbol: Symbol,
    val value: BigDecimal
)