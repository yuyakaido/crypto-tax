package model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.time.ZonedDateTime

@Serializable
data class ExchangeRecord(
    @Contextual val exchangedAt: ZonedDateTime,
    @Contextual val symbol: Symbol,
    @Contextual val fromAmount: BigDecimal,
    @Contextual val toAmount: BigDecimal,
    @Contextual val feeAmount: BigDecimal,
    @Contextual val feeAsset: Asset
) : RecordType() {
    override fun recordedAt(): ZonedDateTime {
        return exchangedAt
    }
    override fun asset(): Asset {
        return symbol.first
    }
}
