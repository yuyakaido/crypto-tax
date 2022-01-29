package model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.time.ZonedDateTime

@Serializable
data class DistributionRecord(
    @Contextual val distributedAt: ZonedDateTime,
    @Contextual val asset: Asset,
    @Contextual val amount: BigDecimal
) : RecordType() {
    override fun recordedAt(): ZonedDateTime {
        return distributedAt
    }
    override fun asset(): Asset {
        return asset
    }
}
