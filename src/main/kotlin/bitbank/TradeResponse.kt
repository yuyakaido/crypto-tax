package bitbank

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import model.Asset
import model.Side
import model.Symbol
import model.TradeRecord
import java.math.BigDecimal
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

@Serializable
data class TradeResponse(
    @SerialName("success") val success: Int,
    @SerialName("data") val data: Data
) {
    @Serializable
    data class Data(
        @SerialName("trades") val trades: List<Trade>
    ) {
        @Serializable
        data class Trade(
            @SerialName("trade_id") val tradeId: Long,
            @SerialName("pair") val pair: String,
            @SerialName("order_id") val orderId: Long,
            @SerialName("side") val side: String,
            @SerialName("type") val type: String,
            @SerialName("amount") @Contextual val amount: BigDecimal,
            @SerialName("price") @Contextual val price: BigDecimal,
            @SerialName("maker_taker") val makerTaker: String,
            @SerialName("fee_amount_base") @Contextual val feeAmountBase: BigDecimal,
            @SerialName("fee_amount_quote") @Contextual val feeAmountQuote: BigDecimal,
            @SerialName("executed_at") val executedAt: Long
        ) {
            fun toTradeRecord(): TradeRecord {
                return TradeRecord(
                    tradedAt = ZonedDateTime.ofInstant(Instant.ofEpochMilli(executedAt), ZoneId.systemDefault()),
                    symbol = Symbol.from(Asset.pair(pair)),
                    side = Side.from(side),
                    tradePrice = price,
                    tradeAmount = amount,
                    feeAmount = feeAmountQuote,
                    feeAsset = Asset.second(pair)
                )
            }
        }
    }
}
