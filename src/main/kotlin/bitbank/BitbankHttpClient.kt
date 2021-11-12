package bitbank

import retrofit2.http.GET

interface BitbankHttpClient {

    @GET("user/spot/trade_history")
    suspend fun getTradeHistory(): TradeResponse

}