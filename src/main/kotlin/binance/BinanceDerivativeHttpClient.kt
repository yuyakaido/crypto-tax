package binance

import retrofit2.http.GET
import retrofit2.http.Query

interface BinanceDerivativeHttpClient {

    @GET("/dapi/v1/userTrades")
    suspend fun getCoinFutureTradeHistory(
        @Query("symbol") symbol: String,
        @Query("startTime") startTime: Long,
        @Query("limit") limit: Int = 1000
    ): List<FutureTradeResponse>

    @GET("/dapi/v1/income")
    suspend fun getCoinFutureProfitLossHistory(
        @Query("symbol") symbol: String,
        @Query("startTime") startTime: Long,
        @Query("limit") limit: Int = 1000
    ): List<IncomeResponse>

}