package bybit

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class AssetExchangeHistoryResponse(
    @SerialName("result") val results: List<Result>
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
}
