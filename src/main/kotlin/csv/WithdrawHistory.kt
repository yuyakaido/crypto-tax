package csv

data class WithdrawHistory(
    override val name: String,
    override val lines: List<ExportableAsCsvLine>
) : ExportableAsCsvFile {
    override val header: String = "WithdrawnAt,Asset,Amount,Fee"
}