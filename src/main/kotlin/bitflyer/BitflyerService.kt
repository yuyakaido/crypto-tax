package bitflyer

import common.Service
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
object BitflyerService : Service {

    override suspend fun execute() {
//        val fiatDeposit = BitflyerDownloader.client.getFiatDepositHistory().map { it.toDepositRecord() }
//        val coinDeposit = BitflyerDownloader.client.getCoinDepositHistory().map { it.toDepositRecord() }
//        BitflyerExporter.exportDepositHistory(fiatDeposit + coinDeposit)
//        val fiatWithdraw = BitflyerDownloader.client.getFiatWithdrawHistory().map { it.toWithdrawRecord() }
//        val coinWithdraw = BitflyerDownloader.client.getCoinWithdrawHistory().map { it.toWithdrawRecord() }
//        BitflyerExporter.exportWithdrawHistory(fiatWithdraw + coinWithdraw)
        val trade = BitflyerImporter.importTradeRecords()
        BitflyerExporter.exportTradeHistory(trade)
    }

}