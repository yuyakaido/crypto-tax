package bitflyer

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import model.Asset
import model.CoinDepositRecord
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.ZoneOffset

@Serializable
data class CoinDepositResponse(
    @SerialName("id") val id: Long,
    @SerialName("order_id") val orderId: String,
    @SerialName("currency_code") val currencyCode: String,
    @SerialName("amount") @Contextual val amount: BigDecimal,
    @SerialName("address") val address: String,
    @SerialName("tx_hash") val txHash: String,
    @SerialName("status") val status: String,
    @SerialName("event_date") val eventDate: String
) {
    fun toCoinDepositRecord(): CoinDepositRecord {
        return CoinDepositRecord(
            depositedAt = LocalDateTime.parse(eventDate).atZone(ZoneOffset.UTC.normalized()),
            asset = Asset.single(currencyCode),
            amount = amount
        )
    }
}
