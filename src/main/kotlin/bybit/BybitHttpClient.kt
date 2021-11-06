package bybit

import retrofit2.http.GET
import retrofit2.http.QueryMap

interface BybitHttpClient {
    @GET("/v2/private/wallet/withdraw/list")
    suspend fun getWithdrawHistory(
        @QueryMap queries: Map<String, String>
    ): WithdrawHistoryResponse

    @GET("/v2/private/exchange-order/list")
    suspend fun getAssetExchangeHistory(
        @QueryMap queries: Map<String, String>
    ): AssetExchangeHistoryResponse

    @GET("/v2/private/execution/list")
    suspend fun getInversePerpetualTradeHistory(
        @QueryMap queries: Map<String, String>
    ): InversePerpetualTradeHistoryResponse

    @GET("/private/linear/trade/execution/list")
    suspend fun getUSDTPerpetualTradeHistory(
        @QueryMap queries: Map<String, String>
    ): USDTPerpetualTradeHistoryResponse

    @GET("/spot/v1/myTrades")
    suspend fun getSpotTradeHistory(
        @QueryMap queries: Map<String, String>
    ): SpotTradeHistoryResponse
}
