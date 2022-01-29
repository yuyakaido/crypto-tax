package binance

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import model.Asset
import model.WithdrawRecord
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@Serializable
data class WithdrawResponse(
    @SerialName("id") val id: String,
    @SerialName("amount") val amount: String,
    @SerialName("transactionFee") val transactionFee: String,
    @SerialName("coin") val coin: String,
    @SerialName("status") val status: Int,
    @SerialName("address") val address: String,
    @SerialName("txId") val txId: String,
    @SerialName("applyTime") val applyTime: String,
    @SerialName("network") val network: String,
    @SerialName("transferType") val transferType: Int,
    @SerialName("info") val info: String,
    @SerialName("confirmNo") val confirmNo: Int,
    @SerialName("walletType") val walletType: Int,
    @SerialName("txKey") val txKey: String
) {
    companion object {
        private val FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    }
    fun toWithdrawRecord(): WithdrawRecord {
        return WithdrawRecord(
            withdrawnAt = LocalDateTime.parse(applyTime, FORMATTER)
                .atZone(ZoneOffset.UTC)
                .withZoneSameInstant(ZoneId.systemDefault()),
            asset = Asset.single(coin),
            amount = BigDecimal(amount),
            fee = BigDecimal(transactionFee)
        )
    }
}
