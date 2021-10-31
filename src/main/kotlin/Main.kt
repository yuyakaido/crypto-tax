import bybit.BybitDownloader
import kotlinx.serialization.ExperimentalSerializationApi
import kotlin.system.exitProcess

@ExperimentalSerializationApi
fun main() {
    println("Started!")
    BybitDownloader.execute()
    println("Completed!")
    exitProcess(0)
}
