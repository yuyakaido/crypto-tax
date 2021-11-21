package poloniex

import kotlinx.serialization.json.JsonObject
import retrofit2.http.*
import java.time.Instant
import java.time.ZonedDateTime

interface PoloniexHttpClient {

    @GET("public")
    suspend fun getChartData(
        @Query("command") command: String = "returnChartData",
        @Query("currencyPair") currencyPair: String = "USDT_BTC",
        @Query("period") period: Long = 300,
        @Query("start") start: Long,
        @Query("end") end: Long
    ): List<ChartDataResponse>

    @FormUrlEncoded
    @POST("tradingApi")
    suspend fun getDepositWithdrawHistory(
        @Field("command") command: String = "returnDepositsWithdrawals",
        @Field("nonce") nonce: Long = Instant.now().toEpochMilli(),
        @Field("start") start: Long = ZonedDateTime.now().minusYears(100).toEpochSecond(),
        @Field("end") end: Long = ZonedDateTime.now().toEpochSecond()
    ): DepositWithdrawHistoryResponse

    @FormUrlEncoded
    @POST("tradingApi")
    suspend fun getTradeHistory(
        @Field("command") command: String = "returnTradeHistory",
        @Field("nonce") nonce: Long = Instant.now().toEpochMilli(),
        @Field("currencyPair") currencyPair: String = "all",
        @Field("start") start: Long = ZonedDateTime.now().minusYears(100).toEpochSecond(),
        @Field("end") end: Long = ZonedDateTime.now().toEpochSecond()
    ): JsonObject

}