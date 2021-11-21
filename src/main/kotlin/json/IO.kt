package json

import kotlinx.serialization.ExperimentalSerializationApi
import java.io.File

@ExperimentalSerializationApi
interface IO {

    enum class Directory(private val path: String) {
        Inputs("inputs"),
        Outputs("outputs"),
        Resources("resources");
        fun toFile(): File {
            return File("${System.getProperty("user.dir")}/${path}")
        }
    }

    private fun getTargetFile(directory: File, name: String): File {
        return File("${directory.path}/${name}.json")
    }

    fun <T> export(file: JsonFile<T>, into: Directory) {
        val targetDirectory = into.toFile()
        targetDirectory.mkdir()
        val targetFile = getTargetFile(targetDirectory, file.name)
        targetFile.createNewFile()
        targetFile.bufferedWriter().apply {
            append(file.json.toString())
        }.close()
    }

    fun import(name: String, from: Directory): String {
        val targetDirectory = from.toFile()
        val targetFile = getTargetFile(targetDirectory, name)
        return targetFile.readText()
    }

}
