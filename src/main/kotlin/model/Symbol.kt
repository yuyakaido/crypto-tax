package model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class Symbol(
    @Contextual val first: Asset,
    @Contextual val second: Asset
) {
    companion object {
        fun from(pair: Pair<Asset, Asset>): Symbol {
            return Symbol(
                first = pair.first,
                second = pair.second
            )
        }
    }
    override fun toString(): String {
        return "$first/$second"
    }
}
