package model

import kotlinx.serialization.Serializable

@Serializable
data class Asset(
    val value: String
) {

    companion object {
        val JPY = single("JPY")
        val BTC = single("BTC")
        val USDT = single("USDT")
        val BUSD = single("BUSD")
        val USDC = single("USDC")

        private val QUOTABLE_LEGAL_ASSETS = listOf("JPY", "USD")
        val QUOTABLE_STABLE_ASSETS = listOf("USDT", "BUSD", "USDC")
        val QUOTABLE_CRYPTO_ASSETS = listOf("BTC", "ETH", "XRP", "BNB")
        private val QUOTABLE_ASSETS = QUOTABLE_CRYPTO_ASSETS + QUOTABLE_STABLE_ASSETS + QUOTABLE_LEGAL_ASSETS

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
            val second = QUOTABLE_ASSETS.first { sanitizedSymbol.endsWith(it) }
            return Asset(second)
        }
    }

    override fun toString(): String {
        return value
    }

}
