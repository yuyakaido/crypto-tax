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
        val symbols = listOf(
            "DOTUSD_PERP",
            "EGLDUSD_PERP",
            "LTCUSD_PERP",
            "BCHUSD_PERP",
            "DOGEUSD_PERP",
            "ADAUSD_PERP"
        )
        val startTime = ZonedDateTime.of(
            LocalDateTime.of(2021, 1, 1, 0, 0, 0),
            ZoneOffset.UTC
        )
        val responses = mutableListOf<FutureTradeResponse>()
        symbols.forEach {
            responses.addAll(
                derivativeClient.getCoinFutureTradeHistory(
                    symbol = it,
                    startTime = startTime.toInstant().toEpochMilli()
                )
            )
        }
        return responses.map { it.toTradeRecord() }
    }

    suspend fun downloadCoinFutureIncomeHistory(): List<IncomeResponse> {
        val startTime = ZonedDateTime.of(
            LocalDateTime.of(2021, 1, 1, 0, 0, 0),
            ZoneOffset.UTC
        )
        return derivativeClient.getCoinFutureIncomeHistory(
            startTime = startTime.toInstant().toEpochMilli()
        )
    }

    suspend fun downloadDistributionHistory(): List<DistributionRecord> {
        val response = spotClient.getDistributionHistory()
        return response.toDistributionRecords()
    }

}