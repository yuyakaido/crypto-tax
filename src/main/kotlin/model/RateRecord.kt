package model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.time.LocalDate

@Serializable
data class RateRecord(
    @Contextual val date: LocalDate,
    @Contextual val price: BigDecimal
)
