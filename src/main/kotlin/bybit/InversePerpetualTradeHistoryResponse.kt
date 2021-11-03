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
data class InversePerpetualTradeHistoryResponse(
    @SerialName("result") val result: Result
) {
    @Serializable
    data class Result(
        @SerialName("trade_list") val tradeList: List<Trade>
    ) {
        @Serializable
        data class Trade(
            @SerialName("trade_time_ms") val tradeTimeMs: Long,
            @SerialName("symbol") val symbol: String,
            @SerialName("side") val side: String,
            @SerialName("exec_price") val execPrice: String,
            @SerialName("exec_qty") val execQty: JsonPrimitive,
            @SerialName("exec_fee") val execFee: String
        )
    }
    fun toTradeHistories(): List<TradeHistory> {
        return result.tradeList
            .map { trade ->
                TradeHistory(
                    tradedAt = ZonedDateTime.ofInstant(Instant.ofEpochMilli(trade.tradeTimeMs), ZoneId.systemDefault()),
                    pair = Asset.pair(trade.symbol),
                    side = Side.from(trade.side),
                    price = BigDecimal(trade.execPrice),
                    qty = BigDecimal(trade.execQty.content),
                    feeQty = BigDecimal(trade.execFee),
                    feeAsset = Asset.first(trade.symbol)
                )
            }
    }
}