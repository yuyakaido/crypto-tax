package bitflyer

import retrofit2.http.GET

interface BitflyerHttpClient {

    @GET("me/getdeposits")
    suspend fun getDepositHistory(): List<DepositResponse>

}