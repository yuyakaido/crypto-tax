package bybit

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SymbolResponse(
    @SerialName("result") val result: List<Result>
) {
    @Serializable
    data class Result(
        @SerialName("name") val name: String,
        @SerialName("alias") val alias: String,
        @SerialName("status") val status: String,
        @SerialName("base_currency") val baseCurrency: String,
        @SerialName("quote_currency") val quoteCurrency: String
    )
}
