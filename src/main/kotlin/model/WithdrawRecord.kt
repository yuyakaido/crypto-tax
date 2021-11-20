package model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.time.ZonedDateTime

@Serializable
data class WithdrawRecord(
    @Contextual val withdrawnAt: ZonedDateTime,
    @Contextual val asset: Asset,
    @Contextual val amount: BigDecimal,
    @Contextual val fee: BigDecimal
)
