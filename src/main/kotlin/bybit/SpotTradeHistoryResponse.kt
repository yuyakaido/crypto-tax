package bybit

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
data class SpotTradeHistoryResponse(
    @SerialName("result") val result: List<Result>
) {

    @Serializable
    data class Result(
        @SerialName("ticketId") val ticketId: String,
        @SerialName("time") val time: String,
        @SerialName("symbol") val symbol: String,
        @SerialName("isBuyer") val isBuyer: Boolean,
        @SerialName("price") val price: String,
        @SerialName("qty") val qty: String,
        @SerialName("commission") val commission: String,
        @SerialName("commissionAsset") val commissionAsset: String
    )

    fun toTradeRecords(symbol: Symbol): List<TradeRecord> {
        return result
            .map { result ->
                TradeRecord(
                    tradedAt = ZonedDateTime.ofInstant(
                        Instant.ofEpochMilli(result.time.toLong()),
                        ZoneId.systemDefault()
                    ),
                    symbol = symbol,
                    side = if (result.isBuyer) { Side.Buy } else { Side.Sell },
                    tradePrice = BigDecimal(result.price),
                    tradeAmount = BigDecimal(result.qty),
                    feeAmount = BigDecimal(result.commission),
                    feeAsset = Asset.single(result.commissionAsset)
                )
            }
    }

}
