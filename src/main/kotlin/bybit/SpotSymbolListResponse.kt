package bybit

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import model.Asset
import model.Symbol

@Serializable
data class SpotSymbolListResponse(
    @SerialName("result") val result: List<Result>
) {

    @Serializable
    data class Result(
        @SerialName("name") val name: String,
        @SerialName("baseCurrency") val baseCurrency: String,
        @SerialName("quoteCurrency") val quoteCurrency: String
    ) {
        fun toSymbol(): Symbol {
            return Symbol.from(
                Asset.single(baseCurrency) to Asset.single(quoteCurrency)
            )
        }
    }

    fun toSymbols(): List<Symbol> {
        return result.map { it.toSymbol() }
    }

}
