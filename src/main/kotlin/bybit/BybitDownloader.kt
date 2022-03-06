package bybit

import common.RetrofitCreator
import kotlinx.coroutines.delay
import kotlinx.serialization.ExperimentalSerializationApi
import model.*
import java.time.LocalDateTime
import java.time.ZoneOffset

@ExperimentalSerializationApi
object BybitDownloader {

    private val apiKey = System.getProperty("BYBIT_API_KEY")
    private val apiSecret = System.getProperty("BYBIT_API_SECRET")
    private val client = RetrofitCreator
        .newInstance(
            baseUrl = "https://api.bybit.com/",
            interceptors = listOf(
                BybitHttpInterceptor(
                    apiKey = apiKey,
                    apiSecret = apiSecret
                )
            )
        )
        .create(BybitHttpClient::class.java)

    suspend fun downloadWithdrawRecords(): List<WithdrawRecord> {
        return client.getWithdrawHistory().toWithdrawRecords()
    }

    suspend fun downloadExchangeRecords(): List<ExchangeRecord> {
        println("Downloading bybit exchange trade history")

        val responses = mutableListOf<ExchangeTradeHistoryResponse>()
        var from: Long? = null
        while (true) {
            val response = client.getExchangeTradeHistory(from = from)
            responses.add(response)
            if (response.result.size < 20) {
                break
            } else {
                from = response.result.last().id
            }
            delay(5000)
        }

        return responses
            .flatMap { it.toExchangeRecords() }
            .distinctBy { it.exchangedAt }
    }

    suspend fun downloadSpotTradeRecords(): List<SpotTradeRecord> {
        println("Downloading bybit spot trade history")

        val records = mutableListOf<SpotTradeRecord>()
        val symbols = client.getSpotSymbolList().toSymbols()
        symbols.forEach { symbol ->
            println(symbol)
            val startTime = LocalDateTime
                .of(2021, 1, 1, 0, 0, 0)
                .toInstant(ZoneOffset.UTC)
                .toEpochMilli()
                .toString()
            var fromId: String? = null
            while (true) {
                val response = client.getSpotTradeHistory(
                    symbol = symbol.toBybitString(),
                    startTime = startTime,
                    fromId = fromId
                )
                records.addAll(response.toTradeRecords(symbol))
                if (response.result.size < 50) {
                    break
                } else {
                    fromId = response.result.last().ticketId
                }
                delay(5000)
            }
        }

        return records
    }

    suspend fun downloadInversePerpetualTradeRecords(): List<FutureTradeRecord> {
        println("Downloading bybit inverse perpetual trade history")

        val records = mutableListOf<FutureTradeRecord>()
        val symbols = listOf(
            Symbol.from(Asset.pair("BTC/USD")),
            Symbol.from(Asset.pair("ETH/USD")),
            Symbol.from(Asset.pair("XRP/USD")),
            Symbol.from(Asset.pair("EOS/USD")),
            Symbol.from(Asset.pair("DOT/USD"))
        )
        symbols.forEach { symbol ->
            println(symbol)
            var page = 1
            while (true) {
                val response = client.getInversePerpetualTradeHistory(
                    symbol = symbol.toBybitString(),
                    page = page++
                )
                records.addAll(response.toFutureTradeRecords(symbol))
                val size = response.result.tradeList?.size ?: 0
                if (size < 50) {
                    break
                }
                delay(5000)
            }
        }

        return records
    }

    suspend fun downloadInversePerpetualProfitLossRecords(): List<ProfitLossRecord> {
        println("Downloading bybit inverse perpetual profit loss history")

        val records = mutableListOf<ProfitLossRecord>()
        val symbols = listOf(
            Symbol.from(Asset.pair("BTC/USD")),
            Symbol.from(Asset.pair("ETH/USD")),
            Symbol.from(Asset.pair("XRP/USD")),
            Symbol.from(Asset.pair("EOS/USD")),
            Symbol.from(Asset.pair("DOT/USD"))
        )
        symbols.forEach { symbol ->
            println(symbol)
            var page = 1
            while (true) {
                val response = client.getInversePerpetualProfitLossHistory(
                    symbol = symbol.toBybitString(),
                    page = page++
                )
                records.addAll(response.toProfitLossRecords(symbol))
                val size = response.result?.data?.size ?: 0
                if (size < 50) {
                    break
                }
                delay(5000)
            }
        }

        return records
    }

    suspend fun downloadUSDTPerpetualTradeRecords(): List<FutureTradeRecord> {
        println("Downloading bybit USDT perpetual trade history")

        val records = mutableListOf<FutureTradeRecord>()
        val symbols = listOf(
            Symbol.from(Asset.pair("BTC/USDT")),
            Symbol.from(Asset.pair("ETH/USDT")),
            Symbol.from(Asset.pair("XRP/USDT")),
            Symbol.from(Asset.pair("BIT/USDT"))
        )
        symbols.forEach { symbol ->
            println(symbol)
            var page = 1
            while (true) {
                val response = client.getUSDTPerpetualTradeHistory(
                    symbol = symbol.toBybitString(),
                    page = page++
                )
                records.addAll(response.toFutureTradeRecords(symbol))
                val size = response.result.data?.size?: 0
                if (size < 50) {
                    break
                }
                delay(5000)
            }
        }

        return records
    }

    suspend fun downloadUSDTPerpetualProfitLossRecords(): List<ProfitLossRecord> {
        println("Downloading bybit inverse perpetual profit loss history")

        val records = mutableListOf<ProfitLossRecord>()
        val symbols = listOf(
            Symbol.from(Asset.pair("BTC/USDT")),
            Symbol.from(Asset.pair("ETH/USDT")),
            Symbol.from(Asset.pair("XRP/USDT")),
            Symbol.from(Asset.pair("BIT/USDT"))
        )
        symbols.forEach { symbol ->
            println(symbol)
            var page = 1
            while (true) {
                val response = client.getUSDTPerpetualProfitLossHistory(
                    symbol = symbol.toBybitString(),
                    page = page++
                )
                records.addAll(response.toProfitLossRecords(symbol))
                val size = response.result?.data?.size ?: 0
                if (size < 50) {
                    break
                }
                delay(5000)
            }
        }

        return records
    }

}
