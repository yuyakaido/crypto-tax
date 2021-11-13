package csv

interface CsvFile {
    val name: String
    val header: String
    val unsortedRows: List<CsvRecord>
    val sortedRows: List<CsvRecord>
}