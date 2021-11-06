package bitflyer

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import model.Asset
import model.WithdrawRecord
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
    fun toWithdrawRecord(): WithdrawRecord {
        return WithdrawRecord(
            withdrawnAt = LocalDateTime.parse(eventDate).atZone(ZoneOffset.UTC.normalized()),
            asset = Asset.single(currencyCode),
            amount = amount,
            fee = BigDecimal(770) // https://bitflyer.com/en-jp/commission
        )
    }
}
