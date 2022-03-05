package binance

import common.RetrofitCreator
import kotlinx.coroutines.delay
import kotlinx.serialization.ExperimentalSerializationApi
import model.*
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

    suspend fun downloadDepositHistory(): List<DepositRecord> {
        return spotClient.getDepositHistory().map { it.toDepositRecord() }
    }

    suspend fun downloadWithdrawHistory(): List<WithdrawRecord> {
        return spotClient.getWithdrawHistory().map { it.toWithdrawRecord() }
    }

    suspend fun downloadSpotTradeHistory(): List<TradeRecord> {
        println("Downloading binance spot trade history")

//        val symbols = client.getSymbols().toSymbols()

        val symbols = listOf(
            Symbol.from(Asset.pair("BCCBTC")),
            Symbol.from(Asset.pair("SHIBUSDT")),
            Symbol.from(Asset.pair("XRPUSDT")),
            Symbol.from(Asset.pair("FILUSDT")),
            Symbol.from(Asset.pair("BNBUSDT")),
            Symbol.from(Asset.pair("BUSDUSDT")),
            Symbol.from(Asset.pair("IOSTBUSD")),
            Symbol.from(Asset.pair("ADABUSD")),
            Symbol.from(Asset.pair("DOTBUSD")),
            Symbol.from(Asset.pair("DOGEBUSD")),
            Symbol.from(Asset.pair("SHIBBUSD")),
            Symbol.from(Asset.pair("QTUMBUSD")),
            Symbol.from(Asset.pair("DARBUSD")),
            Symbol.from(Asset.pair("SANDBUSD")),
            Symbol.from(Asset.pair("BTCBUSD")),
            Symbol.from(Asset.pair("MATICBUSD")),
            Symbol.from(Asset.pair("CHZBUSD")),
            Symbol.from(Asset.pair("DOTBUSD")),
            Symbol.from(Asset.pair("CHZUSDT")),
            Symbol.from(Asset.pair("LTCUSDT")),
            Symbol.from(Asset.pair("XLMUSDT"))
        )

        val responses = mutableListOf<SpotTradeResponse>()
        symbols.forEach {
            val response = spotClient.getSpotTradeHistory(symbol = it.toBinanceString())
            responses.addAll(response)
            println("Downloaded trade history of $it: ${response.size}")
            delay(5000)
        }

        return responses
            .map { it.toTradeRecord() }
            .map {
                if (it.asset() == Asset("BCC")) {
                    it.copy(
                        symbol = it.symbol.copy(
                            first = Asset.single("BCH")
                        )
                    )
                } else {
                    it
                }
            }
    }

    suspend fun downloadCoinFutureTradeHistory(): List<TradeRecord> {
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
        val records = mutableListOf<TradeRecord>()
        symbols.forEach { symbol ->
            val responses = derivativeClient.getCoinFutureTradeHistory(
                symbol = "${symbol.toBinanceString()}_PERP",
                startTime = startTime.toInstant().toEpochMilli()
            )
            println("Downloaded trade history of $symbol: ${responses.size}")
            records.addAll(responses.map { it.toTradeRecord(symbol) })
            delay(5000)
        }
        return records
    }

    suspend fun downloadCoinFutureProfitLossHistory(): List<ProfitLossRecord> {
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
            val response = derivativeClient.getCoinFutureProfitLossHistory(
                symbol = "${symbol.toBinanceString()}_PERP",
                startTime = startTime.toInstant().toEpochMilli()
            )
            records.addAll(response.mapNotNull { it.toProfitLossRecord(symbol) })
        }

        return records
    }

    suspend fun downloadDistributionHistory(): List<DistributionRecord> {
        val response = spotClient.getDistributionHistory()
        return response.toDistributionRecords()
    }

}