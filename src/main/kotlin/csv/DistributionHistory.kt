package csv

import model.DistributionRecord

data class DistributionHistory(
    override val name: String,
    override val unsortedRows: List<DistributionRecord>
) : CsvFile {
    override val header: String = "DistributedAt,Asset,Amount"
    override val sortedRows: List<CsvRecord> = unsortedRows.sortedByDescending { it.distributedAt }
}
