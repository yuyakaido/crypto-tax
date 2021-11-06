package bitflyer

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import model.Asset
import model.CoinWithdrawRecord
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.ZoneOffset

@Serializable
data class CoinWithdrawResponse(
    @SerialName("id") val id: Long,
    @SerialName("order_id") val orderId: String,
    @SerialName("currency_code") val currencyCode: String,
    @SerialName("amount") @Contextual val amount: BigDecimal,
    @SerialName("address") val address: String,
    @SerialName("fee") @Contextual val fee: BigDecimal,
    @SerialName("additional_fee") @Contextual val additionalFee: BigDecimal,
    @SerialName("tx_hash") val txHash: String,
    @SerialName("status") val status: String,
    @SerialName("event_date") val eventDate: String
) {
    fun toCoinWithdrawRecord(): CoinWithdrawRecord {
        return CoinWithdrawRecord(
            withdrawnAt = LocalDateTime.parse(eventDate).atZone(ZoneOffset.UTC.normalized()),
            asset = Asset.single(currencyCode),
            amount = amount,
            fee = fee + additionalFee
        )
    }
}
