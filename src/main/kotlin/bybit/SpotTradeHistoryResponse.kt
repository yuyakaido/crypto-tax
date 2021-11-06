package bybit

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import model.Asset
import model.Side
import model.TradeRecord
import java.math.BigDecimal
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

@Serializable
data class SpotTradeHistoryResponse(
    @SerialName("result") val result: List<Result>
) {
    @Serializable
    data class Result(
        @SerialName("id") val id: String,
        @SerialName("time") val time: String,
        @SerialName("symbol") val symbol: String,
        @SerialName("isBuyer") val isBuyer: Boolean,
        @SerialName("price") val price: String,
        @SerialName("qty") val qty: String,
        @SerialName("commission") val commission: String,
        @SerialName("commissionAsset") val commissionAsset: String
    )
    fun toTradeRecords(): List<TradeRecord> {
        return result
            .map { result ->
                TradeRecord(
                    tradedAt = ZonedDateTime.ofInstant(Instant.ofEpochMilli(result.time.toLong()), ZoneId.systemDefault()),
                    pair = Asset.pair(result.symbol),
                    side = if (result.isBuyer) { Side.Buy } else { Side.Sell },
                    price = BigDecimal(result.price),
                    amount = BigDecimal(result.qty),
                    feeQty = BigDecimal(result.commission),
                    feeAsset = Asset.single(result.commissionAsset)
                )
            }
    }
}
