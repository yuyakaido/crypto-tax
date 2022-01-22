package poloniex

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import model.Asset
import model.Side
import model.Symbol
import model.TradeRecord
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@Serializable
data class TradeResponse(
    @SerialName("globalTradeID") val globalTradeId: Long,
    @SerialName("tradeID") val tradeId: Long,
    @SerialName("date") val date: String,
    @SerialName("type") val type: String,
    @SerialName("rate") @Contextual val rate: BigDecimal,
    @SerialName("fee") @Contextual val fee: BigDecimal,
    @SerialName("amount") @Contextual val amount: BigDecimal,
    @SerialName("total") @Contextual val total: BigDecimal,
    @SerialName("orderNumber") val orderNumber: String
) {
    companion object {
        private val FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")
    }
    fun toTradeRecord(pair: Pair<Asset, Asset>): TradeRecord {
        val symbol = Symbol.from(pair)
        val side = Side.from(type)
        return TradeRecord(
            tradedAt = LocalDateTime
                .parse(date, FORMATTER)
                .atZone(ZoneOffset.UTC)
                .withZoneSameInstant(ZoneId.systemDefault()),
            symbol = symbol,
            side = side,
            tradePrice = rate,
            tradeAmount = amount,
            feeAmount = when (side) {
                Side.Buy -> amount.multiply(fee).setScale(8, RoundingMode.DOWN)
                Side.Sell -> total.multiply(fee).setScale(8, RoundingMode.DOWN)
            },
            feeAsset = when (side) {
                Side.Buy -> symbol.first
                Side.Sell -> symbol.second
            }
        )
    }
}
