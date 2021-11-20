package csv

import model.DepositRecord

data class DepositHistory(
    override val name: String,
    override val unsortedRows: List<DepositRecord>
) : JsonFile<DepositRecord> {
    override val sortedRows: List<DepositRecord> = unsortedRows.sortedByDescending { it.depositedAt }
}
