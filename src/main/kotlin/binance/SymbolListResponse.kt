package binance

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import model.Asset
import model.Symbol

@Serializable
data class SymbolListResponse(
    @SerialName("symbols") val symbols: List<SymbolResponse>
) {
    @Serializable
    data class SymbolResponse(
        @SerialName("symbol") val symbol: String,
        @SerialName("baseAsset") val baseAsset: String,
        @SerialName("quoteAsset") val quoteAsset: String
    )
    fun toSymbols(): List<Symbol> {
        return symbols.map {
            Symbol.from(Asset.single(it.baseAsset) to Asset.single(it.quoteAsset))
        }
    }
}