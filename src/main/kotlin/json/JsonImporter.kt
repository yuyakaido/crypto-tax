package json

import common.RetrofitCreator
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import model.*

@ExperimentalSerializationApi
object JsonImporter : IO {

    fun importRateRecords(name: String): List<RateRecord> {
        return RetrofitCreator.getJson().decodeFromString(
            string = import(
                name = name,
                from = IO.Directory.Resources
            )
        )
    }

    fun importChartRecords(name: String): List<ChartRecord> {
        return RetrofitCreator.getJson().decodeFromString(
            string = import(
                name = name,
                from = IO.Directory.Resources
            )
        )
    }

    fun importTradeRecords(name: String): List<TradeRecord> {
        return RetrofitCreator.getJson().decodeFromString(
            string = import(
                name = name,
                from = IO.Directory.Outputs
            )
        )
    }

    fun importExchangeRecords(name: String): List<ExchangeRecord> {
        return RetrofitCreator.getJson().decodeFromString(
            string = import(
                name = name,
                from = IO.Directory.Outputs
            )
        )
    }

    fun importDistributionRecords(name: String): List<DistributionRecord> {
        return RetrofitCreator.getJson().decodeFromString(
            string = import(
                name = name,
                from = IO.Directory.Outputs
            )
        )
    }

    fun importWithdrawRecords(name: String): List<WithdrawRecord> {
        return RetrofitCreator.getJson().decodeFromString(
            string = import(
                name = name,
                from = IO.Directory.Outputs
            )
        )
    }

    fun importProfitLossRecords(name: String): List<ProfitLossRecord> {
        return RetrofitCreator.getJson().decodeFromString(
            string = import(
                name = name,
                from = IO.Directory.Outputs
            )
        )
    }

}