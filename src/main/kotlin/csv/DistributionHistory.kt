package csv

import model.DistributionRecord

data class DistributionHistory(
    override val name: String,
    override val unsortedRows: List<DistributionRecord>
) : JsonFile<DistributionRecord> {
    override val sortedRows: List<DistributionRecord> = unsortedRows.sortedByDescending { it.distributedAt }
}
