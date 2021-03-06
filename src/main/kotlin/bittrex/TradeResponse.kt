package bittrex

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import model.Asset
import model.Side
import model.Symbol
import model.SpotTradeRecord
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.ZoneId
import java.time.ZonedDateTime

@Serializable
data class TradeResponse(
    @SerialName("id") val id: String,
    @SerialName("marketSymbol") val marketSymbol: String,
    @SerialName("direction") val direction: String,
    @SerialName("type") val type: String,
    @SerialName("quantity") @Contextual val quantity: BigDecimal? = null,
    @SerialName("limit") @Contextual val limit: BigDecimal? = null,
    @SerialName("ceiling") @Contextual val ceiling: BigDecimal? = null,
    @SerialName("timeInForce") val timeInForce: String,
    @SerialName("clientOrderId") val clientOrderId: String? = null,
    @SerialName("fillQuantity") @Contextual val fillQuantity: BigDecimal,
    @SerialName("commission") @Contextual val commission: BigDecimal,
    @SerialName("proceeds") @Contextual val proceeds: BigDecimal,
    @SerialName("status") val status: String,
    @SerialName("createdAt") val createdAt: String,
    @SerialName("updatedAt") val updatedAt: String,
    @SerialName("closedAt") val closedAt: String,
    @SerialName("orderToCancel") val orderToCancel: JsonObject? = null
) {
    fun toTradeRecord(): SpotTradeRecord {
        val symbol = Symbol.from(Asset.pair(marketSymbol))
        return SpotTradeRecord(
            tradedAt = ZonedDateTime.parse(closedAt).withZoneSameInstant(ZoneId.systemDefault()),
            symbol = symbol,
            side = Side.from(direction),
            tradePrice = proceeds.divide(fillQuantity, RoundingMode.FLOOR),
            tradeAmount = fillQuantity,
            feeAmount = commission,
            feeAsset = symbol.second
        )
    }
}
