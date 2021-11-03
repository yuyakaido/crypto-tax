package bybit

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonPrimitive
import model.Asset
import model.Side
import model.TradeHistory
import java.math.BigDecimal
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

@Serializable
data class USDTPerpetualTradeHistoryResponse(
    @SerialName("result") val result: Result
) {
    @Serializable
    data class Result(
        @SerialName("data") val data: List<Data>
    ) {
        @Serializable
        data class Data(
            @SerialName("trade_time_ms") val tradeTimeMs: Long,
            @SerialName("symbol") val symbol: String,
            @SerialName("side") val side: String,
            @SerialName("exec_price") val execPrice: JsonPrimitive,
            @SerialName("exec_qty") val execQty: JsonPrimitive,
            @SerialName("exec_fee") val execFee: JsonPrimitive
        )
    }
    fun toTradeHistories(): List<TradeHistory> {
        return result.data
            .map { trade ->
                TradeHistory(
                    tradedAt = ZonedDateTime.ofInstant(Instant.ofEpochMilli(trade.tradeTimeMs), ZoneId.systemDefault()),
                    pair = Asset.pair(trade.symbol),
                    side = Side.from(trade.side),
                    price = BigDecimal(trade.execPrice.content),
                    qty = BigDecimal(trade.execQty.content),
                    feeQty = BigDecimal(trade.execFee.content),
                    feeAsset = Asset.second(trade.symbol)
                )
            }
    }
}
