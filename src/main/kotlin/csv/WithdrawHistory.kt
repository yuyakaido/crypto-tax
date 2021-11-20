package csv

import model.WithdrawRecord

data class WithdrawHistory(
    override val name: String,
    override val unsortedRows: List<WithdrawRecord>
) : JsonFile<WithdrawRecord> {
    override val sortedRows: List<WithdrawRecord> = unsortedRows.sortedByDescending { it.withdrawnAt }
}
