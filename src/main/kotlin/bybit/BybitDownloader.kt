package bybit

import common.RetrofitCreator
import kotlinx.coroutines.delay
import kotlinx.serialization.ExperimentalSerializationApi
import model.Asset
import model.Symbol
import model.TradeRecord
import model.WithdrawRecord
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

    suspend fun downloadExchangeTradeRecords(): List<TradeRecord> {
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
            .flatMap { it.toTradeRecords() }
            .distinctBy { it.tradedAt }
    }

    suspend fun downloadSpotTradeRecords(): List<TradeRecord> {
        println("Downloading bybit spot trade history")

        val records = mutableListOf<TradeRecord>()
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

    suspend fun downloadInversePerpetualTradeRecords(): List<TradeRecord> {
        println("Downloading bybit inverse perpetual trade history")

        val records = mutableListOf<TradeRecord>()
        val symbols = listOf(
            Symbol.from(Asset.pair("BTC/USD")),
            Symbol.from(Asset.pair("ETH/USD")),
            Symbol.from(Asset.pair("XRP/USD")),
            Symbol.from(Asset.pair("EOS/USD")),
            Symbol.from(Asset.pair("DOT/USD")),
            Symbol.from(Asset.pair("BIT/USD"))
        )
        symbols.forEach { symbol ->
            println(symbol)
            var page = 1
            while (true) {
                val response = client.getInversePerpetualTradeHistory(
                    symbol = symbol.toBybitString(),
                    page = page++
                )
                records.addAll(response.toTradeRecords(symbol))
                val size = response.result.tradeList?.size ?: 0
                if (size < 50) {
                    break
                }
                delay(5000)
            }
        }

        return records
    }

    suspend fun downloadUSDTPerpetualTradeRecords(): List<TradeRecord> {
        println("Downloading bybit USDT perpetual trade history")

        val records = mutableListOf<TradeRecord>()
        val symbols = client.getPerpetualSymbolList()
            .toSymbols()
            .filter { it.second == Asset.USDT }
        symbols.forEach { symbol ->
            println(symbol)
            var page = 1
            while (true) {
                val response = client.getUSDTPerpetualTradeHistory(
                    symbol = symbol.toBybitString(),
                    page = page++
                )
                records.addAll(response.toTradeRecords(symbol))
                val size = response.result.data?.size?: 0
                if (size < 50) {
                    break
                }
                delay(5000)
            }
        }

        return records
    }

}
