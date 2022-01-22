package bybit

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SymbolListResponse(
    @SerialName("result") val result: List<Result>
) {
    @Serializable
    data class Result(
        @SerialName("name") val name: String
    )
}