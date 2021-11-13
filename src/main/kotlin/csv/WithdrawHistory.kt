package csv

import model.WithdrawRecord

data class WithdrawHistory(
    override val name: String,
    override val unsortedRows: List<WithdrawRecord>
) : CsvFile {
    override val header: String = "WithdrawnAt,Asset,Amount,Fee"
    override val sortedRows: List<CsvRecord> = unsortedRows.sortedByDescending { it.withdrawnAt }
}
