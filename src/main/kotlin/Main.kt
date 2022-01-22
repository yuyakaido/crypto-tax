import bitbank.BitbankService
import bitflyer.BitflyerService
import bittrex.BittrexService
import bybit.BybitService
import fx.ForeignExchangeService
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import poloniex.PoloniexService
import tax.TaxService
import kotlin.system.exitProcess

@ExperimentalSerializationApi
fun main() {
    runBlocking {
        println("Started!")
        BitflyerService.execute()
        BitbankService.execute()
        PoloniexService.execute()
        BittrexService.execute()
        BybitService.execute()
        ForeignExchangeService.execute()
        TaxService.execute()
        println("Completed!")
        exitProcess(0)
    }
}
