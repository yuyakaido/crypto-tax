package bittrex

import retrofit2.http.GET

interface BittrexHttpClient {

    @GET("deposits/closed")
    suspend fun getDepositHistory(): List<DepositResponse>

}