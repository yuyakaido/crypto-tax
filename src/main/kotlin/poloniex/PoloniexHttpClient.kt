package poloniex

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

}