package json

import common.RetrofitCreator
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString

@ExperimentalSerializationApi
object JsonImporter : IO {

    fun importTradeHistory(name: String): TradeHistory {
        return TradeHistory(
            name = name,
            records = RetrofitCreator.getJson().decodeFromString(
                string = import(
                    name = name,
                    from = IO.Directory.Inputs
                )
            )
        )
    }

}