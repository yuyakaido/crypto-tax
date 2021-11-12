package model

data class Asset(val value: String) {

    companion object {
        private val BASE_ASSETS = listOf("BTC", "JPY", "USD", "USDT", "BUSD")

        private fun sanitize(symbol: String): String {
            return symbol
                .replace("/", "") // For bitFlyer (BTC/JPY)
                .replace("_", "") // For Poloniex (USDT_BTC)
        }

        /**
         * This method only accepts reversed symbol format of Poloniex (USDT_BTC)
         */
        fun poloniex(symbol: String): Pair<Asset, Asset> {
            val assets = symbol.split("_")
            val reversedSymbol = "${assets.last()}_${assets.first()}"
            val sanitizedSymbol = sanitize(reversedSymbol)
            return pair(sanitizedSymbol)
        }

        /**
         * This method accepts BTCUSDT or BTC/USDT or BTC_USDT
         */
        fun pair(symbol: String): Pair<Asset, Asset> {
            val sanitizedSymbol = sanitize(symbol)
            val first = first(sanitizedSymbol)
            val second = second(sanitizedSymbol)
            return first to second
        }

        fun single(asset: String): Asset {
            return Asset(asset)
        }

        fun first(symbol: String): Asset {
            val sanitizedSymbol = sanitize(symbol)
            val second = second(sanitizedSymbol)
            val first = sanitizedSymbol.replace(second.value, "")
            return Asset(first)
        }

        fun second(symbol: String): Asset {
            val sanitizedSymbol = sanitize(symbol)
            val second = BASE_ASSETS.first { sanitizedSymbol.endsWith(it) }
            return Asset(second)
        }
    }

    override fun toString(): String {
        return value
    }

}
