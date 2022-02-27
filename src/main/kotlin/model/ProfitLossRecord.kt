package model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.time.ZonedDateTime

@Serializable
data class ProfitLossRecord(
    @Contextual val tradedAt: ZonedDateTime,
    @Contextual val symbol: Symbol,
    @Contextual val closedPnl: BigDecimal
) : RecordType() {
    override fun recordedAt(): ZonedDateTime {
        return tradedAt
    }
    override fun asset(): Asset {
        return symbol.first
    }
}