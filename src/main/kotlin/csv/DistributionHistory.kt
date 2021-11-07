package csv

data class DistributionHistory(
    override val name: String,
    override val lines: List<ExportableAsCsvLine>
) : ExportableAsCsvFile {
    override val header: String = "DistributedAt,Asset,Amount"
}
