package csv

data class CoinDepositHistory(
    override val name: String,
    override val lines: List<ExportableAsCsvLine>
) : ExportableAsCsvFile {
    override val header: String = "DepositedAt,Asset,Amount"
}
