package json

import common.RetrofitCreator
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString

@ExperimentalSerializationApi
object JsonImporter : IO {

    fun importTradeHistory(fileName: String): TradeHistory {
        return TradeHistory(
            name = fileName,
            unsortedRows = RetrofitCreator.getJson().decodeFromString(import(fileName))
        )
    }

}