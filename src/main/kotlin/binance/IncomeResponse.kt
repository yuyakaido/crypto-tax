package binance

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import model.ProfitLossRecord
import model.Symbol
import java.math.BigDecimal
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

@Serializable
data class IncomeResponse(
    @SerialName("symbol") val symbol: String,
    @SerialName("incomeType") val incomeType: String,
    @SerialName("income") @Contextual val income: BigDecimal,
    @SerialName("asset") val asset: String,
    @SerialName("info") val info: String,
    @SerialName("time") val time: Long,
    @SerialName("tranId") val tranId: Long,
    @SerialName("tradeId") val tradeId: String
) {
    fun toProfitLossRecord(symbol: Symbol): ProfitLossRecord {
        return ProfitLossRecord(
            tradedAt = ZonedDateTime.ofInstant(
                Instant.ofEpochMilli(time), ZoneId.systemDefault()
            ),
            symbol = symbol,
            value = income
        )
    }
}
