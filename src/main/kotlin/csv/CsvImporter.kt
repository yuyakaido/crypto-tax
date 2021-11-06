package csv

import model.DepositRecord
import model.TradeRecord
import model.WithdrawRecord

interface CsvImporter {
    fun importDepositRecords(): List<DepositRecord>
    fun importWithdrawRecords(): List<WithdrawRecord>
    fun importTradeRecords(): List<TradeRecord>
}