package poloniex

import common.RetrofitCreator
import kotlinx.coroutines.delay
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonArray
import model.Asset
import model.ChartRecord
import model.TradeRecord
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.ZonedDateTime

@ExperimentalSerializationApi
object PoloniexDownloader {

    private val apiKey = System.getProperty("POLONIEX_API_KEY")
    private val apiSecret = System.getProperty("POLONIEX_API_SECRET")
    private val client = RetrofitCreator
        .newInstance(
            baseUrl = "https://poloniex.com/",
            interceptors = listOf(
//                PoloniexHttpInterceptor(
//                    apiKey = apiKey,
//                    apiSecret = apiSecret
//                )
            )
        )
        .create(PoloniexHttpClient::class.java)

    suspend fun downloadChartRecords(from: LocalDateTime, to: LocalDateTime): List<ChartRecord> {
        var start = from
        var end = start.plusDays(1).minusSeconds(1)

        val records = mutableListOf<ChartRecord>()

        while (true) {
            println("Start: $start")
            println("End: $end")

            val startEpochSeconds = start.toEpochSecond(ZoneOffset.UTC)
            val endEpochSeconds = end.toEpochSecond(ZoneOffset.UTC)

            val response = client.getChartData(
                start = startEpochSeconds,
                end = endEpochSeconds
            )

            records.addAll(response.map { it.toChartRecord() })

            start = start.plusDays(1)
            end = end.plusDays(1)

            if (end < to) {
                delay(10000)
            } else {
                break
            }
        }

        return records
    }

    suspend fun downloadDepositWithdrawRecords(): DepositWithdrawHistoryResponse {
        return client.getDepositWithdrawHistory()
    }

    suspend fun downloadTradeRecords(): List<TradeRecord> {
        val response = client.getTradeHistory()
        return response.entries
            .flatMap { entry ->
                val pair = Asset.poloniex(entry.key)
                entry.value.jsonArray.map {
                    val element = RetrofitCreator.getJson().decodeFromJsonElement<TradeResponse>(it)
                    element.toTradeRecord(pair)
                }
            }
    }

}