package bybit

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import model.Asset
import model.ExchangeRecord
import model.Symbol
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@Serializable
data class ExchangeTradeHistoryResponse(
    @SerialName("result") val result: List<Result>
) {

    @Serializable
    data class Result(
        @SerialName("id") val id: Long,
        @SerialName("from_coin") val fromCoin: String,
        @SerialName("to_coin") val toCoin: String,
        @SerialName("from_amount") @Contextual val fromAmount: BigDecimal,
        @SerialName("to_amount") @Contextual val toAmount: BigDecimal,
        @SerialName("exchange_rate") @Contextual val exchangeRate: BigDecimal,
        @SerialName("from_fee") @Contextual val fromFee: BigDecimal,
        @SerialName("created_at") val createdAt: String
    )

    fun toExchangeRecords(): List<ExchangeRecord> {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        return result.map {
            ExchangeRecord(
                exchangedAt = LocalDateTime.parse(it.createdAt, formatter)
                    .atZone(ZoneOffset.UTC)
                    .withZoneSameInstant(ZoneId.systemDefault()),
                symbol = Symbol.from(Asset.single(it.toCoin) to Asset.single(it.fromCoin)),
                fromAmount = it.fromAmount,
                toAmount = it.toAmount,
                feeAmount = it.fromFee,
                feeAsset = Asset.single(it.fromCoin)
            )
        }
    }

}
