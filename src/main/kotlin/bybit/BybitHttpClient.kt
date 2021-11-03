package bybit

import retrofit2.http.GET
import retrofit2.http.QueryMap

interface BybitHttpClient {
    @GET("/v2/private/wallet/withdraw/list")
    suspend fun getWithdrawRecords(
        @QueryMap queries: Map<String, String>
    ): WithdrawRecordResponse

    @GET("/v2/private/exchange-order/list")
    suspend fun getAssetExchangeRecord(
        @QueryMap queries: Map<String, String>
    ): AssetExchangeRecordResponse

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
