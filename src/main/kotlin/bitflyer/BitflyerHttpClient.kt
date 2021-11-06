package bitflyer

import retrofit2.http.GET

interface BitflyerHttpClient {

    @GET("me/getdeposits")
    suspend fun getFiatDepositHistory(): List<FiatDepositResponse>

    @GET("me/getwithdrawals")
    suspend fun getFiatWithdrawHistory(): List<FiatWithdrawResponse>

    @GET("me/getcoinins")
    suspend fun getCoinDepositHistory(): List<CoinDepositResponse>

}