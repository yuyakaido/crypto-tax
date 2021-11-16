package model

import kotlinx.serialization.Serializable

@Serializable
enum class Side {
    Buy, Sell;

    companion object {
        fun from(side: String): Side {
            return values().first { it.name.lowercase() == side.lowercase() }
        }
    }
}
