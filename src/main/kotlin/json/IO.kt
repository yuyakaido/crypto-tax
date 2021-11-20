package json

import kotlinx.serialization.ExperimentalSerializationApi
import java.io.File

@ExperimentalSerializationApi
interface IO {

    private fun getTargetDirectory(): File {
        return File("${System.getProperty("user.dir")}/outputs")
    }

    private fun getTargetFile(targetDirectory: File, fileName: String): File {
        return File("${targetDirectory.path}/${fileName}.json")
    }

    fun <T> export(jsonFile: JsonFile<T>, jsonText: String) {
        val targetDirectory = getTargetDirectory()
        targetDirectory.mkdir()
        val targetFile = getTargetFile(targetDirectory, jsonFile.name)
        targetFile.createNewFile()
        targetFile.bufferedWriter().apply {
            append(jsonText)
        }.close()
    }

    fun import(fileName: String): String {
        val targetDirectory = getTargetDirectory()
        val targetFile = getTargetFile(targetDirectory, fileName)
        return targetFile.readText()
    }

}
