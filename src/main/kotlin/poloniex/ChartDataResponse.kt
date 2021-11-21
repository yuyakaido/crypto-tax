package poloniex

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import model.ChartRecord
import java.math.BigDecimal
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

@Serializable
data class ChartDataResponse(
    @SerialName("date") val date: Long,
    @SerialName("high") @Contextual val high: BigDecimal,
    @SerialName("low") @Contextual val low: BigDecimal,
    @SerialName("open") @Contextual val open: BigDecimal,
    @SerialName("close") @Contextual val close: BigDecimal,
    @SerialName("volume") @Contextual val volume: BigDecimal,
    @SerialName("quoteVolume") @Contextual val quoteVolume: BigDecimal,
    @SerialName("weightedAverage") @Contextual val weightedAverage: BigDecimal
) {
    fun toChartRecord(): ChartRecord {
        return ChartRecord(
            date = ZonedDateTime.ofInstant(Instant.ofEpochSecond(date), ZoneId.systemDefault()),
            price = close
        )
    }
}
