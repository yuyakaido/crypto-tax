package binance

import retrofit2.http.GET
import retrofit2.http.Query

interface BinanceDerivativeHttpClient {

    @GET("/dapi/v1/userTrades")
    suspend fun getCoinFutureTradeHistory(
        @Query("symbol") symbol: String,
        @Query("startTime") startTime: Long
    ): List<FutureTradeResponse>

    @GET("/dapi/v1/income")
    suspend fun getCoinFutureIncomeHistory(
        @Query("startTime") startTime: Long
    ): List<IncomeResponse>

}