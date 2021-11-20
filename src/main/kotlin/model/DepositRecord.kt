package model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.time.ZonedDateTime

@Serializable
data class DepositRecord(
    @Contextual val depositedAt: ZonedDateTime,
    @Contextual val asset: Asset,
    @Contextual val amount: BigDecimal
)
