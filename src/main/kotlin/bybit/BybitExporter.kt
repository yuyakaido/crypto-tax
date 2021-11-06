package bybit

import csv.CsvExporter
import csv.TradeHistory
import csv.WithdrawHistory

object BybitExporter {

    fun exportWithdrawHistory(
        response: WithdrawHistoryResponse
    ) {
        CsvExporter.export(
            WithdrawHistory(
                name = "bybit_withdraw_history",
                lines = response.toWithdrawRecords()
            )
        )
    }

    fun exportInversePerpetualTradeHistory(
        responses: List<InversePerpetualTradeHistoryResponse>
    ) {
        CsvExporter.export(
            TradeHistory(
                name = "bybit_inverse_perpetual_trade_history",
                lines = responses.flatMap { it.toTradeRecords() }
            )
        )
    }

    fun exportUSDTPerpetualTradeHistory(
        responses: List<USDTPerpetualTradeHistoryResponse>
    ) {
        CsvExporter.export(
            TradeHistory(
                name = "bybit_usdt_perpetual_trade_history",
                lines = responses.flatMap { it.toTradeRecords() }
            )
        )
    }

    fun exportSpotTradeHistory(
        responses: List<SpotTradeHistoryResponse>
    ) {
        CsvExporter.export(
            TradeHistory(
                name = "bybit_spot_trade_history",
                lines = responses.flatMap { it.toTradeRecords() }
            )
        )
    }

}