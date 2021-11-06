package bitflyer

import csv.CsvExporter
import csv.DepositHistory
import csv.TradeHistory
import csv.WithdrawHistory
import model.DepositRecord
import model.TradeRecord
import model.WithdrawRecord

object BitflyerExporter {

    fun exportDepositHistory(records: List<DepositRecord>) {
        CsvExporter.export(
            DepositHistory(
                name = "bitflyer_deposit_history",
                lines = records
            )
        )
    }

    fun exportWithdrawHistory(records: List<WithdrawRecord>) {
        CsvExporter.export(
            WithdrawHistory(
                name = "bitflyer_withdraw_history",
                lines = records
            )
        )
    }

    fun exportTradeHistory(records: List<TradeRecord>) {
        CsvExporter.export(
            TradeHistory(
                name = "bitflyer_trade_history",
                lines = records
            )
        )
    }

}