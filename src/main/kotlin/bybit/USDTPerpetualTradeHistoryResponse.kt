package bybit

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonPrimitive
import model.Side
import model.Symbol
import model.TradeRecord
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
        @SerialName("data") val data: List<Data>?
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

    fun toTradeRecords(symbol: Symbol): List<TradeRecord> {
        return result.data
            ?.map { trade ->
                TradeRecord(
                    tradedAt = ZonedDateTime.ofInstant(
                        Instant.ofEpochMilli(trade.tradeTimeMs),
                        ZoneId.systemDefault()
                    ),
                    symbol = symbol,
                    side = Side.from(trade.side),
                    tradePrice = BigDecimal(trade.execPrice.content),
                    tradeAmount = BigDecimal(trade.execQty.content),
                    feeAmount = BigDecimal(trade.execFee.content),
                    feeAsset = symbol.second
                )
            } ?: emptyList()
    }

}
