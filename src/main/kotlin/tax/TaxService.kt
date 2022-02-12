package tax

import common.Service
import json.JsonImporter
import kotlinx.serialization.ExperimentalSerializationApi
import model.*
import java.math.BigDecimal
import java.time.Year
import java.time.ZonedDateTime

@ExperimentalSerializationApi
object TaxService : Service {

    private val btcJpyChartRecords = JsonImporter.importChartRecords("yuyakaido_btc_jpy_chart_history_2017")
        .plus(JsonImporter.importChartRecords("yuyakaido_btc_jpy_chart_history_2018"))
        .plus(JsonImporter.importChartRecords("yuyakaido_btc_jpy_chart_history_2019"))
        .plus(JsonImporter.importChartRecords("yuyakaido_btc_jpy_chart_history_2020"))
        .plus(JsonImporter.importChartRecords("yuyakaido_btc_jpy_chart_history_2021"))

    private val usdJpyChartRecords = JsonImporter.importRateRecords("macrotrends_usd_jpy_chart_history")

    private var wallet = Wallet()

    override suspend fun execute() {
        (2017..2021).forEach { calculate(Year.of(it)) }
    }

    private fun getNearestBtcJpyPrice(tradedAt: ZonedDateTime): BigDecimal {
        val range = tradedAt to tradedAt.plusMinutes(5)
        return btcJpyChartRecords.first {
            range.first < it.date && it.date < range.second
        }.price
    }

    private fun getNearestUsdJpyPrice(tradedAt: ZonedDateTime): BigDecimal {
        return usdJpyChartRecords.firstOrNull() {
            it.date == tradedAt.toLocalDate()
        }?.price ?: BigDecimal(115)
    }

    private fun calculate(year: Year) {
        println("================ ${year.value} ================")

        val bitflyerTradeRecords = JsonImporter.importTradeRecords("bitflyer_trade_history")
        val bitflyerDistributionRecords = JsonImporter.importDistributionRecords("bitflyer_distribution_history")
        val bitflyerWithdrawRecords = JsonImporter.importWithdrawRecords("bitflyer_withdraw_history")
        val bittrexTradeRecords = JsonImporter.importTradeRecords("bittrex_trade_history")
        val bittrexWithdrawRecords = JsonImporter.importWithdrawRecords("bittrex_withdraw_history")
        val poloniexTradeRecords = JsonImporter.importTradeRecords("poloniex_trade_history")
        val poloniexDistributionRecords = JsonImporter.importDistributionRecords("poloniex_distribution_history")
        val poloniexWithdrawRecords = JsonImporter.importWithdrawRecords("poloniex_withdraw_history")
        val bitbankTradeRecords = JsonImporter.importTradeRecords("bitbank_trade_history")
        val binanceSpotTradeRecords = JsonImporter.importTradeRecords("binance_spot_trade_history")
        val binanceDistributionRecords = JsonImporter.importDistributionRecords("binance_distribution_history")
        val allRecords = bitflyerTradeRecords
            .plus(bitflyerDistributionRecords)
            .plus(bitflyerWithdrawRecords)
            .plus(bittrexTradeRecords)
            .plus(bittrexWithdrawRecords)
            .plus(poloniexTradeRecords)
            .plus(poloniexDistributionRecords)
            .plus(poloniexWithdrawRecords)
            .plus(bitbankTradeRecords)
            .plus(binanceSpotTradeRecords)
            .plus(binanceDistributionRecords)
            .filter { it.recordedAt().year == year.value }
            .sortedBy { it.recordedAt() }

        wallet = wallet.addAll(
            holdings = allRecords.groupBy(
                keySelector = {
                    when (it) {
                        is TradeRecord -> {
                            when (it.side) {
                                Side.Buy -> it.symbol.first
                                Side.Sell -> it.symbol.second
                            }
                        }
                        is DistributionRecord -> {
                            it.asset
                        }
                        is WithdrawRecord -> {
                            it.asset
                        }
                    }
                },
                valueTransform = {
                    when (it) {
                        is TradeRecord -> {
                            when (it.side) {
                                Side.Buy -> {
                                    when (it.symbol.second) {
                                        Asset.JPY -> {
                                            it.tradePrice to it.tradeAmount
                                        }
                                        Asset.BTC -> {
                                            val jpyPrice = it.tradePrice.multiply(getNearestBtcJpyPrice(it.tradedAt))
                                            jpyPrice to it.tradeAmount
                                        }
                                        Asset.USDT, Asset.BUSD, Asset.USDC -> {
                                            val jpyPrice = it.tradePrice.multiply(getNearestUsdJpyPrice(it.tradedAt))
                                            jpyPrice to it.tradeAmount
                                        }
                                        else -> {
                                            throw RuntimeException("Unknown symbol: ${it.symbol}")
                                        }
                                    }
                                }
                                Side.Sell -> {
                                    when (it.symbol.second) {
                                        Asset.BTC -> {
                                            val btcAmount = it.tradePrice.multiply(it.tradeAmount)
                                            val jpyAmount = btcAmount.multiply(getNearestBtcJpyPrice(it.tradedAt))
                                            val jpyPrice = jpyAmount.div(btcAmount)
                                            jpyPrice to btcAmount
                                        }
                                        Asset.USDT, Asset.BUSD, Asset.USDC -> {
                                            val baseAmount = it.tradePrice.multiply(it.tradeAmount)
                                            val jpyAmount = baseAmount.multiply(getNearestUsdJpyPrice(it.tradedAt))
                                            val jpyPrice = jpyAmount.div(baseAmount)
                                            jpyPrice to baseAmount
                                        }
                                        else -> {
                                            BigDecimal.ZERO to BigDecimal.ZERO
                                        }
                                    }
                                }
                            }
                        }
                        is DistributionRecord -> {
                            BigDecimal.ZERO to it.amount
                        }
                        is WithdrawRecord -> {
                            BigDecimal.ZERO to BigDecimal.ZERO
                        }
                    }
                }
            ).mapValues { entry ->
                val lastYearHolding = wallet.holdings[entry.key]

                val subTotalCost = entry.value.fold(BigDecimal.ZERO) { total, pair ->
                    total + pair.first.multiply(pair.second)
                }
                val totalCost = if (lastYearHolding == null) {
                    subTotalCost
                } else {
                    subTotalCost + lastYearHolding.averagePrice.multiply(lastYearHolding.amount)
                }
                val subTotalAmount = entry.value.fold(BigDecimal.ZERO) { total, pair ->
                    total + pair.second
                }
                val totalAmount = if (lastYearHolding == null) {
                    subTotalAmount
                } else {
                    subTotalAmount + lastYearHolding.amount
                }
                return@mapValues Holding(
                    amount = totalAmount,
                    averagePrice = if (totalAmount == BigDecimal.ZERO) {
                        BigDecimal.ZERO
                    } else {
                        totalCost.div(totalAmount)
                    }
                )
            }
        )

        val transactions = allRecords
            .filter { it.recordedAt().year == year.value }
            .map {
                when (it) {
                    is TradeRecord -> {
                        when (it.side) {
                            Side.Buy -> {
                                if (it.symbol.second == Asset.BTC) {
                                    val asset = it.symbol.second
                                    val holding = wallet.holdings.getValue(asset)
                                    val quoteAmount = it.tradePrice.multiply(it.tradeAmount)
                                    wallet = wallet.minus(asset, quoteAmount)
                                    wallet = wallet.minus(it.feeAsset, it.feeAmount)
                                    val profitLoss = ProfitLoss(
                                        record = it,
                                        value = quoteAmount.multiply(getNearestBtcJpyPrice(it.tradedAt)) - quoteAmount.multiply(holding.averagePrice)
                                    )
                                    return@map it.symbol.second to profitLoss
                                } else {
                                    wallet = wallet.minus(it.feeAsset, it.feeAmount)
                                    val profitLoss = ProfitLoss(
                                        record = it,
                                        value = BigDecimal.ZERO
                                    )
                                    return@map it.symbol.first to profitLoss
                                }
                            }
                            Side.Sell -> {
                                when (it.symbol.second) {
                                    Asset.JPY -> {
                                        val asset = it.symbol.first
                                        val holding = wallet.holdings.getValue(asset)
                                        wallet = wallet.minus(asset, it.tradeAmount)
                                        wallet = wallet.minus(it.feeAsset, it.feeAmount)
                                        val profitLoss = ProfitLoss(
                                            record = it,
                                            value = it.tradePrice.multiply(it.tradeAmount) - holding.averagePrice.multiply(it.tradeAmount)
                                        )
                                        return@map it.symbol.first to profitLoss
                                    }
                                    Asset.BTC -> {
                                        val asset = it.symbol.first
                                        val holding = wallet.holdings.getValue(it.symbol.first)
                                        wallet = wallet.minus(asset, it.tradeAmount)
                                        wallet = wallet.minus(it.feeAsset, it.feeAmount)
                                        val unitProfit = it.tradePrice.multiply(getNearestBtcJpyPrice(it.tradedAt)) - holding.averagePrice
                                        val profitLoss = ProfitLoss(
                                            record = it,
                                            value = unitProfit.multiply(it.tradeAmount)
                                        )
                                        return@map it.symbol.first to profitLoss
                                    }
                                    Asset.USDT, Asset.BUSD, Asset.USDC -> {
                                        val asset = it.symbol.first
                                        val holding = wallet.holdings.getValue(it.symbol.first)
                                        wallet = wallet.minus(asset, it.tradeAmount)
                                        wallet = wallet.minus(it.feeAsset, it.feeAmount)
                                        val unitProfit = it.tradePrice.multiply(getNearestUsdJpyPrice(it.tradedAt)) - holding.averagePrice
                                        return@map it.symbol.first to ProfitLoss(
                                            record = it,
                                            value = unitProfit.multiply(it.tradeAmount)
                                        )
                                    }
                                    else -> throw RuntimeException("Unknown symbol: ${it.symbol}")
                                }
                            }
                        }
                    }
                    is DistributionRecord -> {
                        val profitLoss = ProfitLoss(
                            record = it,
                            value = when (it.asset) {
                                Asset.JPY -> it.amount
                                Asset.BTC -> it.amount.multiply(getNearestBtcJpyPrice(it.distributedAt))
                                else -> BigDecimal.ZERO
                            }
                        )
                        return@map it.asset to profitLoss
                    }
                    is WithdrawRecord -> {
                        wallet = wallet.minus(it.asset, it.fee)
                        val profitLoss = ProfitLoss(
                            record = it,
                            value = when (it.asset) {
                                Asset.BTC -> {
                                    -it.fee.multiply(getNearestBtcJpyPrice(it.withdrawnAt))
                                }
                                else -> {
                                    -it.fee
                                }
                            }
                        )
                        return@map it.asset to profitLoss
                    }
                }
            }

        println("================ Holdings and Average Price ================")
        wallet.holdings
            .filterValues { it.amount > BigDecimal.ZERO }
            .forEach { println(it) }

        println("================ Profit & Loss ================")
        var totalProfitLoss = BigDecimal.ZERO
        transactions.groupBy(
            keySelector = { it.first },
            valueTransform = { it.second }
        ).forEach { entry ->
            val subProfitLoss = entry.value.fold(BigDecimal.ZERO) { total, transaction ->
                total + transaction.value
            }
            totalProfitLoss += subProfitLoss
            println("${entry.key}: $subProfitLoss")
        }
        println("Total Profit & Loss in ${year.value} is $totalProfitLoss")
    }

}