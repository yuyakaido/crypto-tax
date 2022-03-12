package model

import kotlinx.serialization.Serializable

@Serializable
data class Asset(
    val value: String
) {

    companion object {
        val JPY = single("JPY")
        val BTC = single("BTC")
        val ETH = single("ETH")
        val XRP = single("XRP")
        val EOS = single("EOS")
        val DOT = single("DOT")
        val EGLD = single("EGLD")
        val LTC = single("LTC")
        val BCH = single("BCH")
        val DOGE = single("DOGE")
        val ADA = single("ADA")
        val USDT = single("USDT")
        val BUSD = single("BUSD")
        val USDC = single("USDC")

        /**
         * This method accepts the following reversed symbol format
         *   - USDT_BTC(Poloniex)
         */
        fun poloniex(symbol: String): Pair<Asset, Asset> {
            val assets = symbol.split("_")
            return single(assets.last()) to single(assets.first())
        }

        /**
         * This method accepts the following not reversed symbol format
         *   - BTC/JPY(bitFlyer)
         *   - btc_jpy(bitbank)
         *   - BTC_USDT(Poloniex)
         *   - BTC-USDT(Bittrex)
         */
        fun pair(symbol: String): Pair<Asset, Asset> {
            val assets = symbol.split("/", "_", "-")
            val first = single(assets.first())
            val second = single(assets.last())
            return first to second
        }

        fun single(asset: String): Asset {
            return Asset(asset)
        }

        fun first(symbol: String): Asset {
            return pair(symbol).first
        }

        fun second(symbol: String): Asset {
            return pair(symbol).second
        }
    }

    override fun toString(): String {
        return value
    }

}
