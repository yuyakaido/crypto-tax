package model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.time.ZonedDateTime

@Serializable
data class ChartRecord(
    @Contextual val date: ZonedDateTime,
    @Contextual val price: BigDecimal
)
