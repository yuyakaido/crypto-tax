package bitflyer

import csv.CsvImporter
import model.Asset
import model.DepositRecord
import model.TradeRecord
import model.WithdrawRecord
import java.io.File
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

object BitflyerImporter : CsvImporter {

    private val directory = File("${System.getProperty("user.dir")}/inputs/bitflyer")
    private val file = File("${directory.path}/TradeHistory.csv")

    private val formatter = DateTimeFormatter.ofPattern("MMM d, yyyy HH:mm:ss")

    override fun importDepositRecords(): List<DepositRecord> {
        val lines = file.readLines()
        val header = lines.first()
        val rows = lines.subList(1, lines.size)

        val regex = Regex("\"[^\"]+\"")
        val headers = regex.findAll(header).map { it.value.replace("\"", "") }
        val tradeDateIndex = headers.indexOf("Trade Date")
        val productIndex = headers.indexOf("Product")
        val tradeTypeIndex = headers.indexOf("Trade Type")
        val tradedPriceIndex = headers.indexOf("Traded Price")
        val currency1Index = headers.indexOf("Currency 1")
        val amountCurrency1Index = headers.indexOf("Amount (Currency 1)")
        val feeIndex = headers.indexOf("Fee")
        val jpyRateCurrency1Index = headers.indexOf("JPY Rate (Currency 1)")
        val currency2Index = headers.indexOf("Currency 2")
        val amountCurrency2Index = headers.indexOf("Amount (Currency 2)")
        val counterPartyIndex = headers.indexOf("Counter Party")
        val orderIdIndex = headers.indexOf("Order ID")
        val detailsIndex = headers.indexOf("Details")

        return rows
            .map { row ->
                regex.findAll(row).map { it.value.replace("\"", "") }.toList()
            }
            .filter { columns ->
                val tradeType = columns[tradeTypeIndex]
                tradeType == "Wire Deposit"
            }
            .map { columns ->
                val depositedAt = columns[tradeDateIndex]
                val asset = columns[currency1Index]
                val amount = columns[amountCurrency1Index].replace(",", "")
                DepositRecord(
                    depositedAt = LocalDateTime.parse(depositedAt, formatter).atZone(ZoneOffset.UTC.normalized()),
                    asset = Asset.single(asset),
                    amount = BigDecimal(amount)
                )
            }
    }

    override fun importWithdrawRecords(): List<WithdrawRecord> {
        TODO("Not yet implemented")
    }

    override fun importTradeRecords(): List<TradeRecord> {
        TODO("Not yet implemented")
    }

}