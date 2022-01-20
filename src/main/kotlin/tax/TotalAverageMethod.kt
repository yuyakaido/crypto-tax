package tax

import json.JsonImporter
import kotlinx.serialization.ExperimentalSerializationApi
import model.Asset
import model.Side
import java.math.BigDecimal
import java.time.ZonedDateTime

@ExperimentalSerializationApi
object TotalAverageMethod {

    private val btcJpyChartRecords = JsonImporter.importChartRecords("yuyakaido_btc_jpy_chart_history_2017")

    private fun getNearestBtcJpyPrice(tradedAt: ZonedDateTime): BigDecimal {
        val range = tradedAt to tradedAt.plusMinutes(5)
        return btcJpyChartRecords.first {
            range.first < it.date && it.date < range.second
        }.price
    }

    fun calculate() {
        val bitflyerTradeRecords = JsonImporter.importTradeRecords("bitflyer_trade_history")
        val bittrexTradeRecords = JsonImporter.importTradeRecords("bittrex_trade_history")
        val poloniexTradeRecords = JsonImporter.importTradeRecords("poloniex_trade_history")
        val allTradeRecords = bitflyerTradeRecords
            .plus(bittrexTradeRecords)
            .plus(poloniexTradeRecords)
            .filter { it.tradedAt.year == 2017 }

        val averagePrices = allTradeRecords
            .groupBy { it.symbol.first }
            .mapValues { entry ->
                val buyTradeRecords = entry.value.filter { it.side == Side.Buy }
                val totalAmount = buyTradeRecords.fold(BigDecimal.ZERO) { amount, record ->
                    amount + record.tradeAmount
                }
                val totalCost = buyTradeRecords.fold(BigDecimal.ZERO) { cost, record ->
                    cost + when (record.symbol.second) {
                        Asset("JPY") -> record.tradePrice.multiply(record.tradeAmount)
                        else -> {
                            val costQuote = record.tradePrice.multiply(record.tradeAmount)
                            costQuote.multiply(getNearestBtcJpyPrice(record.tradedAt))
                        }
                    }
                }
                return@mapValues totalCost.div(totalAmount)
            }

        averagePrices.forEach {
            println("${it.key}: ¥${it.value}")
        }

        bitflyerTradeRecords
            .filter { it.tradedAt.year == 2017 }
            .filter { it.side == Side.Sell }
            .forEach {
                val averagePrice = averagePrices.getValue(it.symbol.first)
                val profitLoss = it.tradePrice.multiply(it.tradeAmount) - averagePrice.multiply(it.tradeAmount)
                println("${it.tradedAt}: ${it.symbol}: ¥$profitLoss")
            }
    }

}