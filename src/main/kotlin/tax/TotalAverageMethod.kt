package tax

import json.JsonImporter
import kotlinx.serialization.ExperimentalSerializationApi
import model.Asset
import model.Side
import java.math.BigDecimal

@ExperimentalSerializationApi
object TotalAverageMethod {

    fun calculate() {
//        val tradeRecords = JsonImporter.importTradeRecords("bitflyer_trade_history")
//            .filter { it.tradedAt.year == 2017 }
//            .filter { it.symbol.first == Asset.single("MONA") }
//        val buyTradeRecords = tradeRecords
//            .filter { it.side == Side.Buy }
//        val sellTradeRecords = tradeRecords
//            .filter { it.side == Side.Sell }
//
//        val totalPrice = buyTradeRecords
//            .fold(BigDecimal.ZERO) { price, record ->
//                price + record.tradePrice.multiply(record.tradeAmount)
//            }
//        val totalAmount = buyTradeRecords
//            .fold(BigDecimal.ZERO) { amount, record ->
//                amount + record.tradeAmount
//            }
//        println("TotalPrice = $totalPrice")
//        println("TotalAmount = $totalAmount")
//
//        val totalAveragePrice = totalPrice.div(totalAmount)
//        println("TotalAveragePrice = $totalAveragePrice")
//
//        sellTradeRecords
//            .forEach { record ->
//                val profitLoss = record.tradePrice.multiply(record.tradeAmount) - totalAveragePrice.multiply(record.tradeAmount)
//                println("TradedAt: ${record.tradedAt}, Profit/Loss: $profitLoss")
//            }
    }

}