package poloniex

import kotlinx.serialization.json.JsonObject
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.time.Instant
import java.time.ZonedDateTime

interface PoloniexHttpClient {

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
    suspend fun getTradeRecords(
        @Field("command") command: String = "returnTradeHistory",
        @Field("nonce") nonce: Long = Instant.now().toEpochMilli(),
        @Field("currencyPair") currencyPair: String = "all",
        @Field("start") start: Long = ZonedDateTime.now().minusYears(100).toEpochSecond(),
        @Field("end") end: Long = ZonedDateTime.now().toEpochSecond()
    ): JsonObject

}