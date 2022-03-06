package binance

import kotlinx.serialization.json.JsonElement
import retrofit2.http.GET
import retrofit2.http.Query

interface BinanceSpotHttpClient {

    @GET("api/v3/klines")
    suspend fun getChartHistory(
        @Query("symbol") symbol: String,
        @Query("startTime") startTime: Long,
        @Query("endTime") endTime: Long,
        @Query("interval") interval: String = "5m",
        @Query("limit") limit: Long = 1000
    ): List<List<JsonElement>>

    @GET("sapi/v1/capital/deposit/hisrec")
    suspend fun getDepositHistory(): List<DepositResponse>

    @GET("sapi/v1/capital/withdraw/history")
    suspend fun getWithdrawHistory(): List<WithdrawResponse>

    @GET("api/v3/exchangeInfo")
    suspend fun getSymbols(): SymbolListResponse

    @GET("api/v3/myTrades")
    suspend fun getSpotTradeHistory(
        @Query("symbol") symbol: String,
        @Query("limit") limit: Long = 1000
    ): List<SpotTradeResponse>

    @GET("sapi/v1/asset/assetDividend")
    suspend fun getDistributionHistory(
        @Query("limit") limit: Long = 500
    ): DistributionListResponse

}