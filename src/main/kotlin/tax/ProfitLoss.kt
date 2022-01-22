package tax

import model.Side
import model.Symbol
import java.math.BigDecimal
import java.time.ZonedDateTime

data class ProfitLoss(
    val tradedAt: ZonedDateTime,
    val symbol: Symbol,
    val side: Side,
    val value: BigDecimal
)