package bitflyer

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import model.Asset
import model.FiatWithdrawRecord
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.ZoneOffset

@Serializable
data class FiatWithdrawResponse(
    @SerialName("id") val id: Long,
    @SerialName("order_id") val orderId: String,
    @SerialName("currency_code") val currencyCode: String,
    @SerialName("amount") @Contextual val amount: BigDecimal,
    @SerialName("status") val status: String,
    @SerialName("event_date") val eventDate: String
) {
    fun toFiatWithdrawRecord(): FiatWithdrawRecord {
        return FiatWithdrawRecord(
            withdrawnAt = LocalDateTime.parse(eventDate).atZone(ZoneOffset.UTC.normalized()),
            asset = Asset.single(currencyCode),
            amount = amount
        )
    }
}
