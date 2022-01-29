package binance

import retrofit2.http.GET

interface BinanceHttpClient {

    @GET("sapi/v1/capital/deposit/hisrec")
    suspend fun getDepositHistory(): List<DepositResponse>

    @GET("sapi/v1/capital/withdraw/history")
    suspend fun getWithdrawHistory(): List<WithdrawResponse>

}