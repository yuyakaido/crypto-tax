package model

enum class Asset {
    BTC,
    XRP,
    BIT,
    USD,
    USDT,
    UNKNOWN;

    companion object {
        fun pair(symbol: String): Pair<Asset, Asset> {
            val first = values().first { symbol.startsWith(it.name) }
            val second = values().first { symbol.endsWith(it.name) }
            return first to second
        }
        fun single(asset: String): Asset {
            return values().first { it.name == asset }
        }
        fun first(symbol: String): Asset {
            return values().first { symbol.startsWith(it.name) }
        }
        fun second(symbol: String): Asset {
            return values().first { symbol.endsWith(it.name) }
        }
    }
}
