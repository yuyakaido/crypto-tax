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
data class InterestResponse(
    @SerialName("asset") val asset: String,
    @SerialName("interest") @Contextual val interest: BigDecimal,
    @SerialName("lendingType") val lendingType: String,
    @SerialName("productName") val productName: String,
    @SerialName("time") val time: Long
) {
    fun toDistributionRecord(): DistributionRecord {
        return DistributionRecord(
            distributedAt = ZonedDateTime
                .ofInstant(Instant.ofEpochMilli(time), ZoneOffset.UTC)
                .withZoneSameInstant(ZoneId.systemDefault()),
            asset = Asset.single(asset),
            amount = interest
        )
    }
}