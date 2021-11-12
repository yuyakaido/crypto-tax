package bittrex

import retrofit2.http.GET

interface BittrexHttpClient {

    @GET("deposits/closed")
    suspend fun getDepositHistory(): List<DepositResponse>

    @GET("withdrawals/closed")
    suspend fun getWithdrawHistory(): List<WithdrawResponse>

    @GET("orders/closed")
    suspend fun getTradeHistory(): List<TradeResponse>

}
