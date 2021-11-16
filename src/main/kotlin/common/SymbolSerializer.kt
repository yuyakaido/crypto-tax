package common

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonPrimitive
import model.Asset
import model.Symbol

@ExperimentalSerializationApi
@Serializer(forClass = Symbol::class)
object SymbolSerializer: KSerializer<Symbol> {
    override fun serialize(encoder: Encoder, value: Symbol) {
        val jsonEncoder = encoder as? JsonEncoder
        jsonEncoder?.encodeJsonElement(JsonPrimitive(value.toString()))
    }
    override fun deserialize(decoder: Decoder): Symbol {
        val jsonDecoder = decoder as? JsonDecoder
        return Symbol.from(Asset.pair(requireNotNull(jsonDecoder?.decodeJsonElement()?.jsonPrimitive?.content)))
    }
}
