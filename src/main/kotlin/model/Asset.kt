package model

data class Asset(val value: String) {

    companion object {
        private val BASE_ASSETS = listOf("BTC", "JPY", "USD", "USDT", "BUSD")

        private fun sanitize(symbol: String): String {
            return symbol
                .replace("/", "") // For bitFlyer (BTC/JPY)
                .replace("_", "") // For Poloniex (USDT_BTC)
                .replace("-", "") // For Bittrex (BTC-USDT)
                .uppercase()
        }

        /**
         * This method accepts the following reversed symbol format
         *   - USDT_BTC(Poloniex)
         */
        fun poloniex(symbol: String): Pair<Asset, Asset> {
            val assets = symbol.split("_")
            val reversedSymbol = "${assets.last()}_${assets.first()}"
            val sanitizedSymbol = sanitize(reversedSymbol)
            return pair(sanitizedSymbol)
        }

        /**
         * This method accepts the following not reversed symbol format
         *   - BTC/USDT(bitFlyer)
         *   - btc_jpy(bitbank)
         *   - BTC_USDT(Poloniex)
         *   - BTC-USDT(Bittrex)
         *   - BTCUSDT(Bybit)
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
