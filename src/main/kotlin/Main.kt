import bitflyer.BitflyerService
import bybit.BybitService
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import kotlin.system.exitProcess

@ExperimentalSerializationApi
fun main() {
    runBlocking {
        println("Started!")
        BitflyerService.execute()
        BybitService.execute()
        println("Completed!")
        exitProcess(0)
    }
}
