package json

import kotlinx.serialization.json.JsonElement

interface JsonFile<T> {
    val name: String
    val records: List<T>
    val json: JsonElement
}
