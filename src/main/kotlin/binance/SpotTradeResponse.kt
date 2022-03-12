package binance

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import model.Asset
import model.Side
import model.SpotTradeRecord
import model.Symbol
import java.math.BigDecimal
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime

@Serializable
data class SpotTradeResponse(
    @SerialName("symbol") val symbol: String,
    @SerialName("id") val id: Long,
    @SerialName("orderId") val orderId: Long,
    @SerialName("orderListId") val orderListId: Long,
    @SerialName("price") @Contextual val price: BigDecimal,
    @SerialName("qty") @Contextual val qty: BigDecimal,
    @SerialName("quoteQty") @Contextual val quoteQty: BigDecimal,
    @SerialName("commission") @Contextual val commission: BigDecimal,
    @SerialName("commissionAsset") val commissionAsset: String,
    @SerialName("time") val time: Long,
    @SerialName("isBuyer") val isBuyer: Boolean,
    @SerialName("isMaker") val isMaker: Boolean,
    @SerialName("isBestMatch") val isBestMatch: Boolean
) {
    fun toTradeRecord(symbol: Symbol): SpotTradeRecord {
        return SpotTradeRecord(
            tradedAt = ZonedDateTime
                .ofInstant(Instant.ofEpochMilli(time), ZoneOffset.UTC)
                .withZoneSameInstant(ZoneId.systemDefault()),
            symbol = symbol,
            side = if (isBuyer) { Side.Buy } else { Side.Sell },
            tradePrice = price,
            tradeAmount = qty,
            feeAmount = commission,
            feeAsset = Asset.single(commissionAsset)
        )
    }
}