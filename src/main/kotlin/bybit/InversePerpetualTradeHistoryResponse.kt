package bybit

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import model.ExecType
import model.FutureTradeRecord
import model.Symbol
import java.math.BigDecimal
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

@Serializable
data class InversePerpetualTradeHistoryResponse(
    @SerialName("result") val result: Result
) {

    @Serializable
    data class Result(
        @SerialName("trade_list") val tradeList: List<Trade>?
    ) {
        @Serializable
        data class Trade(
            @SerialName("closed_size") @Contextual val closedSize: BigDecimal,
            @SerialName("cross_seq") val crossSeq: Long,
            @SerialName("exec_fee") @Contextual val execFee: BigDecimal,
            @SerialName("exec_id") val execId: String,
            @SerialName("exec_price") @Contextual val execPrice: BigDecimal,
            @SerialName("exec_qty") @Contextual val execQty: BigDecimal,
            @SerialName("exec_time") val execTime: Long,
            @SerialName("exec_type") val execType: String,
            @SerialName("exec_value") @Contextual val execValue: BigDecimal,
            @SerialName("fee_rate") @Contextual val feeRate: BigDecimal,
            @SerialName("last_liquidity_ind") val lastLiquidityInd: String,
            @SerialName("leaves_qty") @Contextual val leavesQty: BigDecimal,
            @SerialName("nth_fill") val nthFill: Long,
            @SerialName("order_id") val orderId: String,
            @SerialName("order_link_id") val orderLinkId: String,
            @SerialName("order_price") @Contextual val orderPrice: BigDecimal,
            @SerialName("order_qty") @Contextual val orderQty: BigDecimal,
            @SerialName("order_type") val orderType: String,
            @SerialName("side") val side: String,
            @SerialName("symbol") val symbol: String,
            @SerialName("user_id") val userId: Long,
            @SerialName("trade_time_ms") val tradeTimeMs: Long
        )
    }

    fun toFutureTradeRecords(symbol: Symbol): List<FutureTradeRecord> {
        return result.tradeList
            ?.map { trade ->
                FutureTradeRecord(
                    tradedAt = ZonedDateTime.ofInstant(
                        Instant.ofEpochMilli(trade.tradeTimeMs),
                        ZoneId.systemDefault()
                    ),
                    symbol = symbol,
                    type = ExecType.bybit(trade.execType),
                    tradePrice = trade.execPrice,
                    tradeAmount = trade.execValue,
                    feeAmount = trade.execFee,
                    feeAsset = symbol.first
                )
            } ?: emptyList()
    }

}