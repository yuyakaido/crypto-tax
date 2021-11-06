package csv

data class FiatDepositHistory(
    override val name: String,
    override val lines: List<ExportableAsCsvLine>
) : ExportableAsCsvFile {
    override val header: String = "DepositedAt,Asset,Amount"
}
