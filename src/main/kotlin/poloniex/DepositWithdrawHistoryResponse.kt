package poloniex

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import model.Asset
import model.DepositRecord
import model.DistributionRecord
import model.WithdrawRecord
import java.math.BigDecimal
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

@Serializable
data class DepositWithdrawHistoryResponse(
    @SerialName("adjustments") val adjustments: List<Adjustment>,
    @SerialName("deposits") val deposits: List<Deposit>,
    @SerialName("withdrawals") val withdrawals: List<Withdraw>
) {
    @Serializable
    data class Adjustment(
        @SerialName("currency") val currency: String,
        @SerialName("amount") val amount: String,
        @SerialName("timestamp") val timestamp: Long,
        @SerialName("status") val status: String,
        @SerialName("category") val category: String,
        @SerialName("adjustmentTitle") val adjustmentTitle: String,
        @SerialName("adjustmentDesc") val adjustmentDesc: String,
        @SerialName("adjustmentHelp") val adjustmentHelp: String
    ) {
        fun toDistributionRecord(): DistributionRecord {
            return DistributionRecord(
                distributedAt = ZonedDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.systemDefault()),
                asset = Asset.single(currency),
                amount = BigDecimal(amount)
            )
        }
    }
    @Serializable
    data class Deposit(
        @SerialName("depositNumber") val depositNumber: Long,
        @SerialName("currency") val currency: String,
        @SerialName("address") val address: String,
        @SerialName("amount") val amount: String,
        @SerialName("confirmations") val confirmations: Long,
        @SerialName("txid") val txid: String,
        @SerialName("timestamp") val timestamp: Long,
        @SerialName("status") val status: String
    ) {
        fun toDepositRecord(): DepositRecord {
            return DepositRecord(
                depositedAt = ZonedDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.systemDefault()),
                asset = Asset.single(currency),
                amount = BigDecimal(amount)
            )
        }
    }
    @Serializable
    data class Withdraw(
        @SerialName("withdrawalNumber") val withdrawalNumber: Long,
        @SerialName("currency") val currency: String,
        @SerialName("address") val address: String,
        @SerialName("amount") val amount: String,
        @SerialName("fee") val fee: String,
        @SerialName("timestamp") val timestamp: Long,
        @SerialName("status") val status: String,
        @SerialName("ipAddress") val ipAddress: String,
        @SerialName("paymentID") val paymentID: String?
    ) {
        fun toWithdrawRecord(): WithdrawRecord {
            return WithdrawRecord(
                withdrawnAt = ZonedDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.systemDefault()),
                asset = Asset.single(currency),
                amount = BigDecimal(amount),
                fee = BigDecimal(fee)
            )
        }
    }
}
