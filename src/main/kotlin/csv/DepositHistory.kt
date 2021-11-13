package csv

import model.DepositRecord

data class DepositHistory(
    override val name: String,
    override val unsortedRows: List<DepositRecord>
) : CsvFile {
    override val header: String = "DepositedAt,Asset,Amount"
    override val sortedRows: List<CsvRecord> = unsortedRows.sortedByDescending { it.depositedAt }
}
