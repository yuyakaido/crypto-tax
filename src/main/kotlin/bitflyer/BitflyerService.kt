package bitflyer

import common.Service
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
object BitflyerService : Service {

    override suspend fun execute() {
//        val fiatDeposit = BitflyerDownloader.client.getFiatDepositHistory()
//        BitflyerExporter.exportFiatDepositHistory(fiatDeposit)
//        val fiatWithdraw = BitflyerDownloader.client.getFiatWithdrawHistory()
//        BitflyerExporter.exportFiatWithdrawHistory(fiatWithdraw)
//        val coinDeposit = BitflyerDownloader.client.getCoinDepositHistory()
//        BitflyerExporter.exportCoinDepositHistory(coinDeposit)
//        val coinWithdraw = BitflyerDownloader.client.getCoinWithdrawHistory()
//        BitflyerExporter.exportCoinWithdrawHistory(coinWithdraw)
        val deposit = BitflyerImporter.importDepositRecords()
        BitflyerExporter.exportDepositHistory(deposit)
    }

}