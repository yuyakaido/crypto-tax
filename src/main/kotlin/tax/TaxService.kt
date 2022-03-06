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

    private fun getNearestJpyPrice(asset: Asset, tradedAt: ZonedDateTime): BigDecimal {
        return when (asset) {
            Asset.BTC -> getNearestBtcJpyPrice(tradedAt)
            Asset.ETH -> getNearestEthJpyPrice(tradedAt)
            Asset.XRP -> getNearestXrpJpyPrice(tradedAt)
            Asset.EOS -> getNearestEosJpyPrice(tradedAt)
            Asset.DOT -> getNearestDotJpyPrice(tradedAt)
            Asset.USDT, Asset.BUSD, Asset.USDC -> getNearestUsdJpyPrice(tradedAt)
            else -> throw RuntimeException("Unknown asset: $asset")
        }
    }

    private fun getNearestBtcJpyPrice(tradedAt: ZonedDateTime): BigDecimal {
        val range = tradedAt to tradedAt.plusMinutes(5)
        return btcJpyChartRecords.first {
            range.first < it.date && it.date < range.second
        }.price
    }

    private fun getNearestEthJpyPrice(tradedAt: ZonedDateTime): BigDecimal {
        return BigDecimal("200000")
    }

    private fun getNearestXrpJpyPrice(tradedAt: ZonedDateTime): BigDecimal {
        return BigDecimal("100")
    }

    private fun getNearestEosJpyPrice(tradedAt: ZonedDateTime): BigDecimal {
        return BigDecimal("400")
    }

    private fun getNearestDotJpyPrice(tradedAt: ZonedDateTime): BigDecimal {
        return BigDecimal("3000")
    }

    private fun getNearestUsdJpyPrice(tradedAt: ZonedDateTime): BigDecimal {
        return usdJpyChartRecords.first { it.date == tradedAt.toLocalDate() }.price
    }

    private fun calculate(year: Year) {
        println("================ ${year.value} ================")

        val bitflyerTradeRecords = JsonImporter.importSpotTradeRecords("bitflyer_trade_history")
        val bitflyerDistributionRecords = JsonImporter.importDistributionRecords("bitflyer_distribution_history")
        val bitflyerWithdrawRecords = JsonImporter.importWithdrawRecords("bitflyer_withdraw_history")
        val bittrexTradeRecords = JsonImporter.importSpotTradeRecords("bittrex_trade_history")
        val bittrexWithdrawRecords = JsonImporter.importWithdrawRecords("bittrex_withdraw_history")
        val poloniexTradeRecords = JsonImporter.importSpotTradeRecords("poloniex_trade_history")
        val poloniexDistributionRecords = JsonImporter.importDistributionRecords("poloniex_distribution_history")
        val poloniexWithdrawRecords = JsonImporter.importWithdrawRecords("poloniex_withdraw_history")
        val bitbankTradeRecords = JsonImporter.importSpotTradeRecords("bitbank_trade_history")
        val binanceSpotTradeRecords = JsonImporter.importSpotTradeRecords("binance_spot_trade_history")
        val binanceDistributionRecords = JsonImporter.importDistributionRecords("binance_distribution_history")
        val binanceFutureTradeRecords = JsonImporter.importFutureTradeRecords("binance_coin_future_trade_history")
        val binanceFutureProfitLossRecords = JsonImporter.importProfitLossRecords("binance_coin_future_profit_loss_history")
        val bybitExchangeRecords = JsonImporter.importExchangeRecords("bybit_exchange_history")
        val bybitSpotTradeRecords = JsonImporter.importSpotTradeRecords("bybit_spot_trade_history")
        val bybitInverseTradeRecords = JsonImporter.importFutureTradeRecords("bybit_inverse_trade_history")
        val bybitInverseProfitLossRecords = JsonImporter.importProfitLossRecords("bybit_inverse_profit_loss_history")
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
//            .plus(binanceFutureTradeRecords)
//            .plus(binanceFutureProfitLossRecords)
            .plus(bybitExchangeRecords)
            .plus(bybitSpotTradeRecords)
            .plus(bybitInverseTradeRecords)
            .plus(bybitInverseProfitLossRecords)
            .filter { it.recordedAt().year == year.value }
            .sortedBy { it.recordedAt() }

        wallet = wallet.addAll(
            holdings = allRecords.groupBy(
                keySelector = {
                    when (it) {
                        is SpotTradeRecord -> {
                            when (it.side) {
                                Side.Buy -> it.symbol.first
                                Side.Sell -> it.symbol.second
                            }
                        }
                        is FutureTradeRecord -> {
                            it.asset()
                        }
                        is ExchangeRecord -> {
                            it.asset()
                        }
                        is DistributionRecord -> {
                            it.asset
                        }
                        is WithdrawRecord -> {
                            it.asset
                        }
                        is ProfitLossRecord -> {
                            it.symbol.first
                        }
                    }
                },
                valueTransform = {
                    when (it) {
                        is SpotTradeRecord -> {
                            when (it.side) {
                                Side.Buy -> {
                                    when (val quoteAsset = it.symbol.second) {
                                        Asset.JPY -> {
                                            it.tradePrice to it.tradeAmount
                                        }
                                        else -> {
                                            val nearestJpyPrice = getNearestJpyPrice(quoteAsset, it.tradedAt)
                                            val jpyPrice = it.tradePrice.multiply(nearestJpyPrice)
                                            jpyPrice to it.tradeAmount
                                        }
                                    }
                                }
                                Side.Sell -> {
                                    when (val quoteAsset = it.symbol.second) {
                                        Asset.JPY -> {
                                            // 円転は取得原価の計算に影響を与えない
                                            BigDecimal.ZERO to BigDecimal.ZERO
                                        }
                                        else -> {
                                            val baseAmount = it.tradePrice.multiply(it.tradeAmount)
                                            val nearestJpyPrice = getNearestJpyPrice(quoteAsset, it.tradedAt)
                                            nearestJpyPrice to baseAmount
                                        }
                                    }
                                }
                            }
                        }
                        is FutureTradeRecord -> {
                            // 先物取引履歴は取得原価の計算には影響を与えない
                            BigDecimal.ZERO to BigDecimal.ZERO
                        }
                        is ExchangeRecord -> {
                            val nearestJpyPrice = getNearestJpyPrice(it.symbol.first, it.exchangedAt)
                            nearestJpyPrice to it.toAmount
                        }
                        is DistributionRecord -> {
                            // Airdropの取得原価は0円とする
                            BigDecimal.ZERO to it.amount
                        }
                        is WithdrawRecord -> {
                            // 引き出し履歴は取得原価の計算には影響を与えない
                            BigDecimal.ZERO to BigDecimal.ZERO
                        }
                        is ProfitLossRecord -> {
                            // 損益履歴は取得原価の計算には影響を与えない
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
                    is SpotTradeRecord -> {
                        when (it.side) {
                            Side.Buy -> {
                                when (val quoteAsset = it.symbol.second) {
                                    Asset.JPY -> {
                                        // 日本円で暗号資産を購入した場合に損益は発生しない
                                        val profitLoss = ProfitLoss(
                                            record = it,
                                            value = BigDecimal.ZERO
                                        )
                                        return@map it.symbol.first to profitLoss
                                    }
                                    else -> {
                                        // 暗号資産で暗号資産を購入した場合はQuoteAssetを利確した扱いとなる
                                        val quoteAmount = it.tradePrice.multiply(it.tradeAmount)
                                        wallet = wallet.minus(quoteAsset, quoteAmount)
                                        wallet = wallet.minus(it.feeAsset, it.feeAmount)
                                        val holding = wallet.holdings.getValue(quoteAsset)
                                        val nearestJpyPrice = getNearestJpyPrice(quoteAsset, it.tradedAt)
                                        val profitLoss = ProfitLoss(
                                            record = it,
                                            value = quoteAmount.multiply(nearestJpyPrice) - quoteAmount.multiply(holding.averagePrice)
                                        )
                                        return@map it.symbol.second to profitLoss
                                    }
                                }
                            }
                            Side.Sell -> {
                                when (val quoteAsset = it.symbol.second) {
                                    Asset.JPY -> {
                                        val baseAsset = it.symbol.first
                                        val holding = wallet.holdings.getValue(baseAsset)
                                        wallet = wallet.minus(baseAsset, it.tradeAmount)
                                        wallet = wallet.minus(it.feeAsset, it.feeAmount)
                                        val profitLoss = ProfitLoss(
                                            record = it,
                                            value = it.tradePrice.multiply(it.tradeAmount) - holding.averagePrice.multiply(it.tradeAmount)
                                        )
                                        return@map it.symbol.first to profitLoss
                                    }
                                    else -> {
                                        val baseAsset = it.symbol.first
                                        val holding = wallet.holdings.getOrDefault(
                                            key = it.symbol.first,
                                            defaultValue = Holding(it.tradeAmount, BigDecimal.ZERO)
                                        )
                                        wallet = wallet.minus(baseAsset, it.tradeAmount)
                                        wallet = wallet.minus(it.feeAsset, it.feeAmount)
                                        val nearestJpyPrice = getNearestJpyPrice(quoteAsset, it.tradedAt)
                                        val unitProfit = it.tradePrice.multiply(nearestJpyPrice) - holding.averagePrice
                                        val profitLoss = ProfitLoss(
                                            record = it,
                                            value = unitProfit.multiply(it.tradeAmount)
                                        )
                                        return@map it.symbol.first to profitLoss
                                    }
                                }
                            }
                        }
                    }
                    is FutureTradeRecord -> {
                        val baseAsset = it.asset()
                        val nearestJpyPrice = getNearestJpyPrice(baseAsset, it.tradedAt)
                        return@map baseAsset to ProfitLoss(
                            record = it,
                            value = it.feeAmount.multiply(nearestJpyPrice)
                        )
                    }
                    is ExchangeRecord -> {
                        // 暗号資産同士を交換した場合、QuoteAssetを利確した扱いとなる
                        val quoteAsset = it.symbol.second
                        wallet = wallet.minus(quoteAsset, it.fromAmount)
                        wallet = wallet.minus(it.feeAsset, it.feeAmount)
                        val holding = wallet.holdings.getValue(quoteAsset)
                        val nearestJpyPrice = getNearestJpyPrice(quoteAsset, it.exchangedAt)
                        val profitLoss = ProfitLoss(
                            record = it,
                            value = it.fromAmount.multiply(nearestJpyPrice) - it.fromAmount.multiply(holding.averagePrice)
                        )
                        return@map quoteAsset to profitLoss
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
                    is ProfitLossRecord -> {
                        val nearestJpyPrice = getNearestJpyPrice(it.asset(), it.tradedAt)
                        val profitLoss = ProfitLoss(
                            record = it,
                            value = it.value.multiply(nearestJpyPrice)
                        )
                        return@map it.asset() to profitLoss
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