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

@ExperimentalSerializationApi
@Serializer(forClass = Asset::class)
object AssetSerializer: KSerializer<Asset> {
    override fun serialize(encoder: Encoder, value: Asset) {
        val jsonEncoder = encoder as? JsonEncoder
        jsonEncoder?.encodeJsonElement(JsonPrimitive(value.toString()))
    }
    override fun deserialize(decoder: Decoder): Asset {
        val jsonDecoder = decoder as? JsonDecoder
        return Asset.single(requireNotNull(jsonDecoder?.decodeJsonElement()?.jsonPrimitive?.content))
    }
}
