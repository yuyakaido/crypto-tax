package tax

import model.TradeRecord
import java.math.BigDecimal

data class ProfitLoss(
    val tradeRecord: TradeRecord,
    val value: BigDecimal
)