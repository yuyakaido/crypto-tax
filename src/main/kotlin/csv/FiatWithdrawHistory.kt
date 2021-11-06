package csv

data class FiatWithdrawHistory(
    override val name: String,
    override val lines: List<ExportableAsCsvLine>
) : ExportableAsCsvFile {
    override val header: String = "WithdrawnAt,Asset,Amount"
}
