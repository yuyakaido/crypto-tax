import bitflyer.BitflyerService
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
        BybitService.execute()
        println("Completed!")
        exitProcess(0)
    }
}
