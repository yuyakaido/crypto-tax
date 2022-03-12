package model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.time.ZonedDateTime

@Serializable
data class ProfitLossRecord(
    @Contextual val tradedAt: ZonedDateTime,
    @Contextual val symbol: Symbol,
    @Contextual val value: BigDecimal
) : RecordType() {
    override fun recordedAt(): ZonedDateTime {
        return tradedAt
    }
    override fun asset(): Asset {
        return if (symbol.second == Asset.USDT) {
            symbol.second
        } else {
            symbol.first
        }
    }
}