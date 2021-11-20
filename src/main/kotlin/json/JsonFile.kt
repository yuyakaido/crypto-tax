package json

interface JsonFile<T> {
    val name: String
    val unsortedRows: List<T>
    val sortedRows: List<T>
}