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
import java.time.LocalDate

@ExperimentalSerializationApi
@Serializer(forClass = LocalDate::class)
object LocalDateSerializer: KSerializer<LocalDate> {
    override fun serialize(encoder: Encoder, value: LocalDate) {
        val jsonEncoder = encoder as? JsonEncoder
        jsonEncoder?.encodeJsonElement(JsonPrimitive(value.toString()))
    }
    override fun deserialize(decoder: Decoder): LocalDate {
        val jsonDecoder = decoder as? JsonDecoder
        return LocalDate.parse(jsonDecoder?.decodeJsonElement()?.jsonPrimitive?.content)
    }
}
