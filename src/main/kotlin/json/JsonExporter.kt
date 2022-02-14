package json

import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
object JsonExporter : IO {

    fun export(file: RateHistory) {
        export(
            file = file,
            into = IO.Directory.Resources
        )
    }

    fun export(file: ChartHistory) {
        export(
            file = file,
            into = IO.Directory.Resources
        )
    }

    fun export(file: DepositHistory) {
        export(
            file = file,
            into = IO.Directory.Outputs
        )
    }

    fun export(file: WithdrawHistory) {
        export(
            file = file,
            into = IO.Directory.Outputs
        )
    }

    fun export(file: DistributionHistory) {
        export(
            file = file,
            into = IO.Directory.Outputs
        )
    }

    fun export(file: TradeHistory) {
        export(
            file = file,
            into = IO.Directory.Outputs
        )
    }

    fun export(file: ProfitLossHistory) {
        export(
            file = file,
            into = IO.Directory.Outputs
        )
    }

}