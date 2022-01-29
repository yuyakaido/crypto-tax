package bybit

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import model.Asset
import model.Side
import model.Symbol
import model.TradeRecord
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@Serializable
data class ExchangeHistoryResponse(
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
    fun toTradeRecords(): List<TradeRecord> {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        return result.map {
            TradeRecord(
                tradedAt = LocalDateTime.parse(it.createdAt, formatter)
                    .atZone(ZoneOffset.UTC)
                    .withZoneSameInstant(ZoneId.systemDefault()),
                symbol = when {
                    Asset.QUOTABLE_STABLE_ASSETS.contains(it.fromCoin) -> {
                        Symbol.from(Asset.single(it.toCoin) to Asset.single(it.fromCoin))
                    }
                    Asset.QUOTABLE_STABLE_ASSETS.contains(it.toCoin) -> {
                        Symbol.from(Asset.single(it.fromCoin) to Asset.single(it.toCoin))
                    }
                    Asset.QUOTABLE_CRYPTO_ASSETS.contains(it.fromCoin) -> {
                        Symbol.from(Asset.single(it.toCoin) to Asset.single(it.fromCoin))
                    }
                    Asset.QUOTABLE_CRYPTO_ASSETS.contains(it.toCoin) -> {
                        Symbol.from(Asset.single(it.fromCoin) to Asset.single(it.toCoin))
                    }
                    else -> {
                        throw IllegalStateException("Unknown symbol: from = ${it.fromCoin}, to = ${it.toCoin}")
                    }
                },
                side = when {
                    Asset.QUOTABLE_STABLE_ASSETS.contains(it.fromCoin) -> Side.Buy
                    Asset.QUOTABLE_STABLE_ASSETS.contains(it.toCoin) -> Side.Sell
                    Asset.QUOTABLE_CRYPTO_ASSETS.contains(it.fromCoin) -> Side.Buy
                    Asset.QUOTABLE_CRYPTO_ASSETS.contains(it.toCoin) -> Side.Sell
                    else -> throw IllegalStateException("Failed to detect side: from = ${it.fromCoin}, to = ${it.toCoin}")
                },
                tradePrice = it.exchangeRate,
                tradeAmount = it.toAmount,
                feeAmount = it.fromFee,
                feeAsset = Asset.single(it.fromCoin)
            )
        }
    }
}
