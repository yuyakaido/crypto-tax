package bitflyer

import model.Asset
import model.Side
import model.TradeRecord
import java.io.File
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

object BitflyerImporter {

    private const val TRADE_DATE = "Trade Date"
    private const val PRODUCT = "Product"
    private const val TRADE_TYPE = "Trade Type"
    private const val TRADED_PRICE = "Traded Price"
    private const val CURRENCY_1 = "Currency 1"
    private const val AMOUNT_CURRENCY_1 = "Amount (Currency 1)"
    private const val FEE = "Fee"
    private const val JPY_RATE_CURRENCY_1 = "JPY Rate (Currency 1)"
    private const val CURRENCY_2 = "Currency 2"
    private const val AMOUNT_CURRENCY_2 = "Amount (Currency 2)"
    private const val COUNTER_PARTY = "Counter Party"
    private const val ORDER_ID = "Order ID"
    private const val DETAILS = "Details"

    private const val TRADE_TYPE_DEPOSIT = "Wire Deposit"
    private const val TRADE_TYPE_WITHDRAW = "Withdrawal"
    private const val TRADE_TYPE_BUY = "Buy"
    private const val TRADE_TYPE_SELL = "Sell"

    private val directory = File("${System.getProperty("user.dir")}/inputs/bitflyer")
    private val file = File("${directory.path}/TradeHistory.csv")

    private val lines = file.readLines()
    private val header = lines.first()
    private val rows = lines.subList(1, lines.size)

    private val regex = Regex("\"[^\"]+\"")
    private val headers = regex.findAll(header).map { it.value.replace("\"", "") }
    private val tradeDateIndex = headers.indexOf(TRADE_DATE)
    private val productIndex = headers.indexOf(PRODUCT)
    private val tradeTypeIndex = headers.indexOf(TRADE_TYPE)
    private val tradedPriceIndex = headers.indexOf(TRADED_PRICE)
    private val currency1Index = headers.indexOf(CURRENCY_1)
    private val amountCurrency1Index = headers.indexOf(AMOUNT_CURRENCY_1)
    private val feeIndex = headers.indexOf(FEE)
    private val jpyRateCurrencyIndex = headers.indexOf(JPY_RATE_CURRENCY_1)
    private val currency2Index = headers.indexOf(CURRENCY_2)
    private val amountCurrency2Index = headers.indexOf(AMOUNT_CURRENCY_2)
    private val counterPartyIndex = headers.indexOf(COUNTER_PARTY)
    private val orderIdIndex = headers.indexOf(ORDER_ID)
    private val detailsIndex = headers.indexOf(DETAILS)

    private val formatter = DateTimeFormatter.ofPattern("MMM d, yyyy HH:mm:ss")

    private fun getFilteredRows(type: String): List<List<String>> {
        return rows
            .map { row ->
                regex.findAll(row).map { it.value.replace("\"", "") }.toList()
            }
            .filter { columns ->
                val tradeType = columns[tradeTypeIndex]
                tradeType == type
            }
    }

    fun importTradeRecords(): List<TradeRecord> {
        val rows = getFilteredRows(TRADE_TYPE_BUY) + getFilteredRows(TRADE_TYPE_SELL)
        return rows
            .map { columns ->
                val tradedAt = columns[tradeDateIndex]
                val pair = columns[productIndex]
                val side = columns[tradeTypeIndex]
                val tradePrice = columns[tradedPriceIndex].replace(",", "")
                val tradeAmount = columns[amountCurrency1Index].replace("-", "")
                val feeAmount = columns[feeIndex].replace("-", "")
                val feeAsset = Asset.first(pair)
                TradeRecord(
                    tradedAt = LocalDateTime.parse(tradedAt, formatter).atZone(ZoneOffset.UTC.normalized()),
                    pair = Asset.pair(pair),
                    side = Side.from(side),
                    tradePrice = BigDecimal(tradePrice),
                    tradeAmount = BigDecimal(tradeAmount),
                    feeAmount = BigDecimal(feeAmount),
                    feeAsset = feeAsset
                )
            }
    }

}