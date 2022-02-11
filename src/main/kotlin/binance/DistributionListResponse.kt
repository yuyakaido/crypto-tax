package binance

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import model.Asset
import model.DistributionRecord
import java.math.BigDecimal
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime

@Serializable
data class DistributionListResponse(
    @SerialName("total") val total: Int,
    @SerialName("rows") val rows: List<DistributionResponse>
) {

    @Serializable
    data class DistributionResponse(
        @SerialName("id") val id: Long,
        @SerialName("amount") @Contextual val amount: BigDecimal,
        @SerialName("asset") val asset: String,
        @SerialName("divTime") val divTime: Long,
        @SerialName("enInfo") val enInfo: String,
        @SerialName("tranId") val tranId: Long
    ) {
        fun toDistributionRecord(): DistributionRecord {
            return DistributionRecord(
                distributedAt = ZonedDateTime
                    .ofInstant(Instant.ofEpochMilli(divTime), ZoneOffset.UTC)
                    .withZoneSameInstant(ZoneId.systemDefault()),
                asset = Asset.single(asset),
                amount = amount
            )
        }
    }

    fun toDistributionRecords(): List<DistributionRecord> {
        return rows.map { it.toDistributionRecord() }
    }

}
