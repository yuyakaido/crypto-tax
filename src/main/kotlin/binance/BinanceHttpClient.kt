package binance

import retrofit2.http.GET
import retrofit2.http.Query

interface BinanceHttpClient {

    @GET("sapi/v1/capital/deposit/hisrec")
    suspend fun getDepositHistory(): List<DepositResponse>

    @GET("sapi/v1/capital/withdraw/history")
    suspend fun getWithdrawHistory(): List<WithdrawResponse>

    @GET("api/v3/exchangeInfo")
    suspend fun getSymbols(): SymbolListResponse

    @GET("api/v3/myTrades")
    suspend fun getSpotTradeHistory(
        @Query("symbol") symbol: String
    ): List<SpotTradeResponse>

}