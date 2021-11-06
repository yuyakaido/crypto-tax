package csv

import java.io.File

object CsvExporter {

    fun export(exportable: ExportableAsCsvFile) {
        val outputDirectory = File("${System.getProperty("user.dir")}/build/outputs")
        outputDirectory.mkdir()
        val outputFile = File("${outputDirectory.path}/${exportable.name}.csv")
        outputFile.createNewFile()
        outputFile.bufferedWriter().apply {
            appendLine(exportable.header)
            exportable.lines.forEach { appendLine(it.csv) }
        }.close()
    }

}