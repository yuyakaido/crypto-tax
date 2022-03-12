package binance

import common.RetrofitCreator
import kotlinx.coroutines.delay
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.long
import model.*
import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.ZonedDateTime

@ExperimentalSerializationApi
object BinanceDownloader {

    private val apiKey = System.getProperty("BINANCE_API_KEY")
    private val apiSecret = System.getProperty("BINANCE_API_SECRET")
    private val spotClient = RetrofitCreator
        .newInstance(
            baseUrl = "https://api.binance.com/",
            interceptors = listOf(
                BinanceHttpInterceptor(
                    apiKey = apiKey,
                    apiSecret = apiSecret
                )
            )
        )
        .create(BinanceSpotHttpClient::class.java)
    private val derivativeClient = RetrofitCreator
        .newInstance(
            baseUrl = "https://dapi.binance.com/",
            interceptors = listOf(
                BinanceHttpInterceptor(
                    apiKey = apiKey,
                    apiSecret = apiSecret
                )
            )
        )
        .create(BinanceDerivativeHttpClient::class.java)

    suspend fun downloadChartRecords(
        symbol: Symbol,
        from: LocalDateTime,
        to: LocalDateTime
    ): List<ChartRecord> {
        var start = from
        var end = start.plusDays(1).minusSeconds(1)

        val records = mutableListOf<ChartRecord>()

        while (true) {
            println("Symbol: $symbol, Start: $start, End: $end")

            val startEpochMillis = start.toInstant(ZoneOffset.UTC).toEpochMilli()
            val endEpochMillis = end.toInstant(ZoneOffset.UTC).toEpochMilli()

            val responses = spotClient.getChartHistory(
                symbol = symbol.toBinanceString(),
                startTime = startEpochMillis,
                endTime = endEpochMillis
            )

            records.addAll(
                responses.map {
                    ChartRecord(
                        date = ZonedDateTime.ofInstant(
                            Instant.ofEpochMilli(it[0].jsonPrimitive.long),
                            ZoneOffset.UTC
                        ),
                        price = BigDecimal(it[4].jsonPrimitive.content)
                    )
                }
            )

            start = start.plusDays(1)
            end = end.plusDays(1)

            if (end <= to) {
                delay(5000)
            } else {
                break
            }
        }

        return records
    }

    suspend fun downloadDepositHistory(): List<DepositRecord> {
        println("Downloading binance deposit history")
        return spotClient.getDepositHistory().map { it.toDepositRecord() }
    }

    suspend fun downloadWithdrawHistory(): List<WithdrawRecord> {
        println("Downloading binance withdraw history")
        return spotClient.getWithdrawHistory().map { it.toWithdrawRecord() }
    }

    suspend fun downloadSpotTradeHistory(): List<SpotTradeRecord> {
        println("Downloading binance spot trade history")

//        val symbols = spotClient.getSymbols().toSymbols()

        val symbols = listOf(
            Symbol.from(Asset.pair("BCC/BTC")),
            Symbol.from(Asset.pair("SHIB/USDT")),
            Symbol.from(Asset.pair("XRP/USDT")),
            Symbol.from(Asset.pair("FIL/USDT")),
            Symbol.from(Asset.pair("BNB/USDT")),
            Symbol.from(Asset.pair("BUSD/USDT")),
            Symbol.from(Asset.pair("IOST/BUSD")),
            Symbol.from(Asset.pair("ADA/BUSD")),
            Symbol.from(Asset.pair("DOT/BUSD")),
            Symbol.from(Asset.pair("DOGE/BUSD")),
            Symbol.from(Asset.pair("SHIB/BUSD")),
            Symbol.from(Asset.pair("QTUM/BUSD")),
            Symbol.from(Asset.pair("DAR/BUSD")),
            Symbol.from(Asset.pair("SAND/BUSD")),
            Symbol.from(Asset.pair("BTC/BUSD")),
            Symbol.from(Asset.pair("MATIC/BUSD")),
            Symbol.from(Asset.pair("CHZ/BUSD")),
            Symbol.from(Asset.pair("DOT/BUSD")),
            Symbol.from(Asset.pair("CHZ/USDT")),
            Symbol.from(Asset.pair("LTC/USDT")),
            Symbol.from(Asset.pair("XLM/USDT"))
        )

        val responses = mutableMapOf<Symbol, List<SpotTradeResponse>>()
        symbols.forEach { symbol ->
            val response = spotClient.getSpotTradeHistory(symbol = symbol.toBinanceString())
            responses[symbol] = response
            println("$symbol: ${response.size}")
            delay(5000)
        }

        return responses
            .flatMap { entry ->
                entry.value.map { it.toTradeRecord(entry.key) }
            }
            .map {
                if (it.asset() == Asset("BCC")) {
                    it.copy(
                        symbol = it.symbol.copy(
                            first = Asset.single("BCH")
                        ),
                        feeAsset = Asset.single("BCH")
                    )
                } else {
                    it
                }
            }
    }

    suspend fun downloadCoinFutureTradeHistory(): List<FutureTradeRecord> {
        println("Downloading binance coin future trade history")

        val symbols = listOf(
            Symbol.from(Asset.pair("DOT/USD")),
            Symbol.from(Asset.pair("EGLD/USD")),
            Symbol.from(Asset.pair("LTC/USD")),
            Symbol.from(Asset.pair("BCH/USD")),
            Symbol.from(Asset.pair("DOGE/USD")),
            Symbol.from(Asset.pair("ADA/USD")),
        )
        val startTime = ZonedDateTime.of(
            LocalDateTime.of(2021, 1, 1, 0, 0, 0),
            ZoneOffset.UTC
        )
        val records = mutableListOf<FutureTradeRecord>()
        symbols.forEach { symbol ->
            val responses = derivativeClient.getCoinFutureTradeHistory(
                symbol = "${symbol.toBinanceString()}_PERP",
                startTime = startTime.toInstant().toEpochMilli()
            )
            println("$symbol: ${responses.size}")
            records.addAll(responses.map { it.toTradeRecord(symbol) })
            delay(5000)
        }
        return records
    }

    suspend fun downloadCoinFutureProfitLossHistory(): List<ProfitLossRecord> {
        println("Downloading binance coin future profit loss history")

        val symbols = listOf(
            Symbol.from(Asset.pair("DOT/USD")),
            Symbol.from(Asset.pair("EGLD/USD")),
            Symbol.from(Asset.pair("LTC/USD")),
            Symbol.from(Asset.pair("BCH/USD")),
            Symbol.from(Asset.pair("DOGE/USD")),
            Symbol.from(Asset.pair("ADA/USD")),
        )
        val startTime = ZonedDateTime.of(
            LocalDateTime.of(2021, 1, 1, 0, 0, 0),
            ZoneOffset.UTC
        )
        val records = mutableListOf<ProfitLossRecord>()
        symbols.forEach { symbol ->
            val responses = derivativeClient.getCoinFutureProfitLossHistory(
                symbol = "${symbol.toBinanceString()}_PERP",
                startTime = startTime.toInstant().toEpochMilli()
            )
            println("$symbol: ${responses.size}")
            records.addAll(responses.map { it.toProfitLossRecord(symbol) })
            delay(5000)
        }

        return records
    }

    suspend fun downloadDistributionHistory(): List<DistributionRecord> {
        val response = spotClient.getDistributionHistory()
        return response.toDistributionRecords()
    }

}