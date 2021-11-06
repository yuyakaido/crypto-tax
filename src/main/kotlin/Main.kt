import bitflyer.BitflyerDownloader
import bybit.BybitDownloader
import kotlinx.serialization.ExperimentalSerializationApi
import kotlin.system.exitProcess

@ExperimentalSerializationApi
fun main() {
    println("Started!")
    BitflyerDownloader.execute()
    BybitDownloader.execute()
    println("Completed!")
    exitProcess(0)
}
