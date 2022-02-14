package bybit

import kotlinx.serialization.json.JsonElement
import retrofit2.http.GET
import retrofit2.http.Query

interface BybitHttpClient {

    @GET("v2/private/wallet/withdraw/list")
    suspend fun getWithdrawHistory(): WithdrawHistoryResponse

    @GET("v2/private/exchange-order/list")
    suspend fun getExchangeTradeHistory(
        @Query("from") from: Long? = null
    ): ExchangeTradeHistoryResponse

    @GET("spot/v1/symbols")
    suspend fun getSpotSymbolList(): SpotSymbolListResponse

    @GET("spot/v1/myTrades")
    suspend fun getSpotTradeHistory(
        @Query("symbol") symbol: String,
        @Query("startTime") startTime: String,
        @Query("fromId") fromId: String?
    ): SpotTradeHistoryResponse

    @GET("v2/public/symbols")
    suspend fun getPerpetualSymbolList(): PerpetualSymbolListResponse

    @GET("v2/private/execution/list")
    suspend fun getInversePerpetualTradeHistory(
        @Query("symbol") symbol: String,
        @Query("page") page: Int
    ): InversePerpetualTradeHistoryResponse

    @GET("v2/private/trade/closed-pnl/list")
    suspend fun getInversePerpetualProfitLossHistory(
        @Query("symbol") symbol: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int = 50
    ): PerpetualProfitLossHistoryResponse

    @GET("private/linear/trade/execution/list")
    suspend fun getUSDTPerpetualTradeHistory(
        @Query("symbol") symbol: String,
        @Query("page") page: Int
    ): USDTPerpetualTradeHistoryResponse

    @GET("private/linear/trade/closed-pnl/list")
    suspend fun getUSDTPerpetualProfitLossHistory(
        @Query("symbol") symbol: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int = 50
    ): PerpetualProfitLossHistoryResponse

}
