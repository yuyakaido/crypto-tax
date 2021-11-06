package bitflyer

import csv.CsvExporter
import csv.DepositHistory
import csv.WithdrawHistory
import model.DepositRecord
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

}