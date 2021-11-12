import bitflyer.BitflyerService
import bittrex.BittrexService
import bybit.BybitService
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import poloniex.PoloniexService
import kotlin.system.exitProcess

@ExperimentalSerializationApi
fun main() {
    runBlocking {
        println("Started!")
        BitflyerService.execute()
        PoloniexService.execute()
        BittrexService.execute()
        BybitService.execute()
        println("Completed!")
        exitProcess(0)
    }
}
