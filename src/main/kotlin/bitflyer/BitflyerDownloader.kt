package bitflyer

import common.RetrofitCreator
import kotlinx.serialization.ExperimentalSerializationApi
import model.DepositRecord
import model.WithdrawRecord

@ExperimentalSerializationApi
object BitflyerDownloader {

    private val apiKey = System.getProperty("BITFLYER_API_KEY")
    private val apiSecret = System.getProperty("BITFLYER_API_SECRET")

    val client: BitflyerHttpClient = RetrofitCreator
        .newInstance(
            baseUrl = "https://api.bitflyer.com/v1/",
            interceptors = listOf(BitflyerHttpInterceptor(apiKey, apiSecret))
        )
        .create(BitflyerHttpClient::class.java)

    suspend fun downloadDepositRecords(): List<DepositRecord> {
        val fiatDeposit = client.getFiatDepositHistory().map { it.toDepositRecord() }
        val coinDeposit = client.getCoinDepositHistory().map { it.toDepositRecord() }
        return fiatDeposit + coinDeposit
    }

    suspend fun downloadWithdrawRecords(): List<WithdrawRecord> {
        val fiatWithdraw = client.getFiatWithdrawHistory().map { it.toWithdrawRecord() }
        val coinWithdraw = client.getCoinWithdrawHistory().map { it.toWithdrawRecord() }
        return fiatWithdraw + coinWithdraw
    }

}