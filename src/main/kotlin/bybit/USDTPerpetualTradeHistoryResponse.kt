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
data class USDTPerpetualTradeHistoryResponse(
    @SerialName("result") val result: Result
) {

    @Serializable
    data class Result(
        @SerialName("data") val data: List<Data>?
    ) {
        @Serializable
        data class Data(
            @SerialName("order_id") val orderId: String,
            @SerialName("order_link_id") val orderLinkId: String,
            @SerialName("side") val side: String,
            @SerialName("symbol") val symbol: String,
            @SerialName("exec_id") val execId: String,
            @SerialName("price") @Contextual val price: BigDecimal,
            @SerialName("order_price") @Contextual val orderPrice: BigDecimal,
            @SerialName("order_qty") @Contextual val orderQty: BigDecimal,
            @SerialName("order_type") val orderType: String,
            @SerialName("fee_rate") @Contextual val feeRate: BigDecimal,
            @SerialName("exec_price") @Contextual val execPrice: BigDecimal,
            @SerialName("exec_type") val execType: String,
            @SerialName("exec_qty") @Contextual val execQty: BigDecimal,
            @SerialName("exec_fee") @Contextual val execFee: BigDecimal,
            @SerialName("exec_value") @Contextual val execValue: BigDecimal,
            @SerialName("leaves_qty") @Contextual val leavesQty: BigDecimal,
            @SerialName("closed_size") @Contextual val closedSize: BigDecimal,
            @SerialName("last_liquidity_ind") val lastLiquidityInd: String,
            @SerialName("trade_time") val tradeTime: Long,
            @SerialName("trade_time_ms") val tradeTimeMs: Long,
        )
    }

    fun toFutureTradeRecords(symbol: Symbol): List<FutureTradeRecord> {
        return result.data
            ?.map { trade ->
                FutureTradeRecord(
                    tradedAt = ZonedDateTime.ofInstant(
                        Instant.ofEpochMilli(trade.tradeTimeMs),
                        ZoneId.systemDefault()
                    ),
                    symbol = symbol,
                    type = ExecType.bybit(trade.execType),
                    tradePrice = trade.execPrice,
                    tradeAmount = trade.execQty,
                    feeAmount = trade.execFee,
                    feeAsset = symbol.second
                )
            } ?: emptyList()
    }

}
