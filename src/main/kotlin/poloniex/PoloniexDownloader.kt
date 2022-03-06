package poloniex

import common.RetrofitCreator
import kotlinx.coroutines.delay
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonArray
import model.Asset
import model.ChartRecord
import model.SpotTradeRecord
import java.time.LocalDateTime
import java.time.ZoneOffset

@ExperimentalSerializationApi
object PoloniexDownloader {

    private val apiKey = System.getProperty("POLONIEX_API_KEY")
    private val apiSecret = System.getProperty("POLONIEX_API_SECRET")
    private val client = RetrofitCreator
        .newInstance(
            baseUrl = "https://poloniex.com/",
            interceptors = listOf(
                PoloniexHttpInterceptor(
                    apiKey = apiKey,
                    apiSecret = apiSecret
                )
            )
        )
        .create(PoloniexHttpClient::class.java)

    suspend fun downloadDepositWithdrawRecords(): DepositWithdrawHistoryResponse {
        return client.getDepositWithdrawHistory()
    }

    suspend fun downloadTradeRecords(): List<SpotTradeRecord> {
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