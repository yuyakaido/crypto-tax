package bybit

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface BybitHttpClient {
    @GET("/v2/private/wallet/withdraw/list")
    suspend fun getWithdrawHistory(): WithdrawHistoryResponse

    @GET("/v2/private/exchange-order/list")
    suspend fun getAssetExchangeHistory(
        @QueryMap queries: Map<String, String>
    ): AssetExchangeHistoryResponse

    @GET("/v2/private/execution/list")
    suspend fun getInversePerpetualTradeHistory(
        @Query("symbol") symbol: String = "BTCUSD",
        @Query("page") page: Int
    ): InversePerpetualTradeHistoryResponse

    @GET("/private/linear/trade/execution/list")
    suspend fun getUSDTPerpetualTradeHistory(
        @Query("symbol") symbol: String = "BTCUSDT",
        @Query("page") page: Int
    ): USDTPerpetualTradeHistoryResponse

    @GET("/spot/v1/symbols")
    suspend fun getSpotSymbolList(): SymbolListResponse

    @GET("/spot/v1/myTrades")
    suspend fun getSpotTradeHistory(
        @Query("symbol") symbol: String,
        @Query("startTime") startTime: String,
        @Query("fromId") fromId: String?
    ): SpotTradeHistoryResponse
}
