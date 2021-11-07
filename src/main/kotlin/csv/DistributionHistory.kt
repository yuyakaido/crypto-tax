package csv

import model.DistributionRecord

data class DistributionHistory(
    override val name: String,
    override val lines: List<DistributionRecord>
) : ExportableAsCsvFile {
    override val header: String = "DistributedAt,Asset,Amount"
}
