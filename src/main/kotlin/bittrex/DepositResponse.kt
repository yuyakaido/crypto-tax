package bittrex

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import model.Asset
import model.DepositRecord
import java.math.BigDecimal
import java.time.ZonedDateTime

@Serializable
data class DepositResponse(
    @SerialName("id") val id: String,
    @SerialName("currencySymbol") val currencySymbol: String,
    @SerialName("quantity") @Contextual val quantity: BigDecimal,
    @SerialName("cryptoAddress") val cryptoAddress: String,
    @SerialName("fundsTransferMethodId") val fundsTransferMethodId: String? = null,
    @SerialName("cryptoAddressTag") val cryptoAddressTag: String? = null,
    @SerialName("txId") val txId: String,
    @SerialName("confirmations") val confirmations: Int,
    @SerialName("updatedAt") val updatedAt: String,
    @SerialName("completedAt") val completedAt: String,
    @SerialName("status") val status: String,
    @SerialName("source") val source: String,
    @SerialName("accountId") val accountId: String? = null,
    @SerialName("error") val error: JsonElement? = null
) {
    fun toDepositRecord(): DepositRecord {
        return DepositRecord(
            depositedAt = ZonedDateTime.parse(completedAt),
            asset = Asset.single(currencySymbol),
            amount = quantity
        )
    }
}