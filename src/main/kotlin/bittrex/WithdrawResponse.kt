package bittrex

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import model.Asset
import model.WithdrawRecord
import java.math.BigDecimal
import java.time.ZonedDateTime

@Serializable
data class WithdrawResponse(
    @SerialName("id") val id: String,
    @SerialName("currencySymbol") val currencySymbol: String,
    @SerialName("quantity") @Contextual val quantity: BigDecimal,
    @SerialName("cryptoAddress") val cryptoAddress: String,
    @SerialName("cryptoAddressTag") val cryptoAddressTag: String? = null,
    @SerialName("fundsTransferMethodId") val fundsTransferMethodId: String? = null,
    @SerialName("txCost") @Contextual val txCost: BigDecimal,
    @SerialName("txId") val txId: String,
    @SerialName("status") val status: String,
    @SerialName("createdAt") val createdAt: String,
    @SerialName("completedAt") val completedAt: String,
    @SerialName("clientWithdrawalId") val clientWithdrawalId: String? = null,
    @SerialName("target") val target: String,
    @SerialName("accountId") val accountId: String? = null,
    @SerialName("error") val error: JsonObject? = null
) {
    fun toWithdrawRecord(): WithdrawRecord {
        return WithdrawRecord(
            withdrawnAt = ZonedDateTime.parse(completedAt),
            asset = Asset.single(currencySymbol),
            amount = quantity,
            fee = txCost
        )
    }
}
