package csv

import model.WithdrawRecord

data class WithdrawHistory(
    override val name: String,
    override val lines: List<WithdrawRecord>
) : ExportableAsCsvFile {
    override val header: String = "WithdrawnAt,Asset,Amount,Fee"
}
