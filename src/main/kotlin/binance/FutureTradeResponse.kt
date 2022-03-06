package binance

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import model.Asset
import model.Side
import model.Symbol
import model.SpotTradeRecord
import java.math.BigDecimal
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime

@Serializable
data class FutureTradeResponse(
    @SerialName("symbol") val symbol: String,
    @SerialName("id") val id: Long,
    @SerialName("orderId") val orderId: Long,
    @SerialName("pair") val pair: String,
    @SerialName("side") val side: String,
    @SerialName("price") @Contextual val price: BigDecimal,
    @SerialName("qty") @Contextual val qty: BigDecimal,
    @SerialName("realizedPnl") @Contextual val realizedPnl: BigDecimal,
    @SerialName("marginAsset") val marginAsset: String,
    @SerialName("baseQty") @Contextual val baseQty: BigDecimal,
    @SerialName("commission") @Contextual val commission: BigDecimal,
    @SerialName("commissionAsset") val commissionAsset: String,
    @SerialName("time") val time: Long,
    @SerialName("positionSide") val positionSide: String,
    @SerialName("buyer") val buyer: Boolean,
    @SerialName("maker") val maker: Boolean
) {
    fun toTradeRecord(symbol: Symbol): SpotTradeRecord {
        return SpotTradeRecord(
            tradedAt = ZonedDateTime
                .ofInstant(Instant.ofEpochMilli(time), ZoneOffset.UTC)
                .withZoneSameInstant(ZoneId.systemDefault()),
            symbol = symbol,
            side = Side.from(side),
            tradePrice = price,
            tradeAmount = baseQty,
            feeAmount = commission,
            feeAsset = Asset.single(commissionAsset)
        )
    }
}
