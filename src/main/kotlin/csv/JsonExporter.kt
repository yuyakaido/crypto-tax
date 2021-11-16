package csv

import common.RetrofitCreator
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.encodeToJsonElement
import java.io.File

@ExperimentalSerializationApi
object JsonExporter {

    fun export(file: TradeHistory) {
        val outputDirectory = File("${System.getProperty("user.dir")}/outputs")
        outputDirectory.mkdir()
        val outputFile = File("${outputDirectory.path}/${file.name}.json")
        outputFile.createNewFile()
        outputFile.bufferedWriter().apply {
            val json = RetrofitCreator.getJson().encodeToJsonElement(file.unsortedRows)
            append(json.toString())
        }.close()
    }

}