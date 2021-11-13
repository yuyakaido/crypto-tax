package csv

import java.io.File

object CsvExporter {

    fun export(file: CsvFile) {
        val outputDirectory = File("${System.getProperty("user.dir")}/outputs")
        outputDirectory.mkdir()
        val outputFile = File("${outputDirectory.path}/${file.name}.csv")
        outputFile.createNewFile()
        outputFile.bufferedWriter().apply {
            appendLine(file.header)
            file.sortedRows.forEach { appendLine(it.csv) }
        }.close()
    }

}