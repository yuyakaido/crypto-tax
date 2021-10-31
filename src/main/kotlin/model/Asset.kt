package model

enum class Asset {
    XRP,
    BIT,
    USDT;

    companion object {
        fun pair(symbol: String): Pair<Asset, Asset> {
            val first = values().first { symbol.startsWith(it.name) }
            val second = values().first { symbol.endsWith(it.name) }
            return first to second
        }
        fun single(asset: String): Asset {
            return values().first { asset == it.name }
        }
    }
}
