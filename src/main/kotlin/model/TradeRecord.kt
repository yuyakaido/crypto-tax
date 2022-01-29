package model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.time.ZonedDateTime

@Serializable
data class TradeRecord(
    @Contextual val tradedAt: ZonedDateTime,
    @Contextual val symbol: Symbol,
    @Contextual val side: Side,
    @Contextual val tradePrice: BigDecimal,
    @Contextual val tradeAmount: BigDecimal,
    @Contextual val feeAmount: BigDecimal,
    @Contextual val feeAsset: Asset
) : RecordType() {
    override fun recordedAt(): ZonedDateTime {
        return tradedAt
    }
    override fun asset(): Asset {
        return symbol.first
    }
}
