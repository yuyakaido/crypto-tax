package bybit

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import model.Asset
import model.CoinWithdrawRecord
import java.math.BigDecimal
import java.time.ZonedDateTime

@Serializable
data class WithdrawHistoryResponse(
    @SerialName("result") val result: Result
) {
    @Serializable
    data class Result(
        @SerialName("data") val data: List<Data>
    ) {
        @Serializable
        data class Data(
            @SerialName("id") val id: Long,
            @SerialName("coin") val coin: String,
            @SerialName("amount") val amount: String,
            @SerialName("fee") val fee: String,
            @SerialName("updated_at") val updatedAt: String
        )
    }
    fun toWithdrawRecords(): List<CoinWithdrawRecord> {
        return result.data
            .map { d ->
                CoinWithdrawRecord(
                    withdrawnAt = ZonedDateTime.parse(d.updatedAt),
                    asset = Asset.single(d.coin),
                    amount = BigDecimal(d.amount),
                    fee = BigDecimal(d.fee)
                )
            }
    }
}
