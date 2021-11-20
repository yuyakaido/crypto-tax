package json

import common.RetrofitCreator
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.encodeToJsonElement
import java.io.File

@ExperimentalSerializationApi
object JsonExporter {

    fun export(file: DepositHistory) {
        val outputDirectory = File("${System.getProperty("user.dir")}/outputs")
        outputDirectory.mkdir()
        val outputFile = File("${outputDirectory.path}/${file.name}.json")
        outputFile.createNewFile()
        outputFile.bufferedWriter().apply {
            val json = RetrofitCreator.getJson().encodeToJsonElement(file.sortedRows)
            append(json.toString())
        }.close()
    }

    fun export(file: WithdrawHistory) {
        val outputDirectory = File("${System.getProperty("user.dir")}/outputs")
        outputDirectory.mkdir()
        val outputFile = File("${outputDirectory.path}/${file.name}.json")
        outputFile.createNewFile()
        outputFile.bufferedWriter().apply {
            val json = RetrofitCreator.getJson().encodeToJsonElement(file.sortedRows)
            append(json.toString())
        }.close()
    }

    fun export(file: DistributionHistory) {
        val outputDirectory = File("${System.getProperty("user.dir")}/outputs")
        outputDirectory.mkdir()
        val outputFile = File("${outputDirectory.path}/${file.name}.json")
        outputFile.createNewFile()
        outputFile.bufferedWriter().apply {
            val json = RetrofitCreator.getJson().encodeToJsonElement(file.sortedRows)
            append(json.toString())
        }.close()
    }

    fun export(file: TradeHistory) {
        val outputDirectory = File("${System.getProperty("user.dir")}/outputs")
        outputDirectory.mkdir()
        val outputFile = File("${outputDirectory.path}/${file.name}.json")
        outputFile.createNewFile()
        outputFile.bufferedWriter().apply {
            val json = RetrofitCreator.getJson().encodeToJsonElement(file.sortedRows)
            append(json.toString())
        }.close()
    }

}