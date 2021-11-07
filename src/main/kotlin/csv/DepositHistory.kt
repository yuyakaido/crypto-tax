package csv

import model.DepositRecord

data class DepositHistory(
    override val name: String,
    override val lines: List<DepositRecord>
) : ExportableAsCsvFile {
    override val header: String = "DepositedAt,Asset,Amount"
}
