package bybit

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
data class PerpetualProfitLossHistoryResponse(
    @SerialName("result") val result: Result?
) {

    @Serializable
    data class Result(
        @SerialName("data") val data: List<Data>?
    ) {
        @Serializable
        data class Data(
            @SerialName("id") val id: Long,
            @SerialName("user_id") val userId: Long,
            @SerialName("symbol") val symbol: String,
            @SerialName("order_id") val orderId: String,
            @SerialName("side") val side: String,
            @SerialName("qty") @Contextual val qty: BigDecimal,
            @SerialName("order_price") @Contextual val orderPrice: BigDecimal,
            @SerialName("order_type") val orderType: String,
            @SerialName("exec_type") val execType: String,
            @SerialName("closed_size") @Contextual val closedSize: BigDecimal,
            @SerialName("cum_entry_value") @Contextual val cumEntryValue: BigDecimal,
            @SerialName("avg_entry_price") @Contextual val avgEntryPrice: BigDecimal,
            @SerialName("cum_exit_value") @Contextual val cumExitValue: BigDecimal,
            @SerialName("avg_exit_price") @Contextual val avgExitPrice: BigDecimal,
            @SerialName("closed_pnl") @Contextual val closedPnl: BigDecimal,
            @SerialName("fill_count") val fillCount: Int,
            @SerialName("leverage") val leverage: Int,
            @SerialName("created_at") val createdAt: Long
        ) {
            fun toProfitLossRecord(symbol: Symbol): ProfitLossRecord {
                return ProfitLossRecord(
                    tradedAt = ZonedDateTime.ofInstant(Instant.ofEpochSecond(createdAt), ZoneId.systemDefault()),
                    symbol = symbol,
                    value = closedPnl
                )
            }
        }
    }

    fun toProfitLossRecords(symbol: Symbol): List<ProfitLossRecord> {
        return result?.data?.map { it.toProfitLossRecord(symbol) } ?: emptyList()
    }

}
