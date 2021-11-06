package csv

interface ExportableAsCsvFile {
    val name: String
    val header: String
    val lines: List<ExportableAsCsvLine>
}