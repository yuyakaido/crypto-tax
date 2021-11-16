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
import java.time.ZonedDateTime

@ExperimentalSerializationApi
@Serializer(forClass = ZonedDateTime::class)
object ZonedDateTimeSerializer: KSerializer<ZonedDateTime> {
    override fun serialize(encoder: Encoder, value: ZonedDateTime) {
        val jsonEncoder = encoder as? JsonEncoder
        jsonEncoder?.encodeJsonElement(JsonPrimitive(value.toString()))
    }
    override fun deserialize(decoder: Decoder): ZonedDateTime {
        val jsonDecoder = decoder as? JsonDecoder
        return ZonedDateTime.parse(jsonDecoder?.decodeJsonElement()?.jsonPrimitive?.content)
    }
}
