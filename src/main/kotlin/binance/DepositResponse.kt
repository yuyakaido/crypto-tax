package binance

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import model.Asset
import model.DepositRecord
import java.math.BigDecimal
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime

@Serializable
data class DepositResponse(
    @SerialName("amount") val amount: String,
    @SerialName("coin") val coin: String,
    @SerialName("network") val network: String,
    @SerialName("status") val status: Int,
    @SerialName("address") val address: String,
    @SerialName("addressTag") val addressTag: String,
    @SerialName("txId") val txId: String,
    @SerialName("insertTime") val insertTime: Long,
    @SerialName("transferType") val transferType: Int,
    @SerialName("confirmTimes") val confirmTimes: String,
    @SerialName("unlockConfirm") val unlockConfirm: Int,
    @SerialName("walletType") val walletType: Int
) {
    fun toDepositRecord(): DepositRecord {
        return DepositRecord(
            depositedAt = ZonedDateTime
                .ofInstant(Instant.ofEpochMilli(insertTime), ZoneOffset.UTC)
                .withZoneSameInstant(ZoneId.systemDefault()),
            asset = Asset.single(coin),
            amount = BigDecimal(amount)
        )
    }
}
