package model

enum class ExecType(
    val binance: String,
    val bybit: String
) {
    Trade(
        binance = "",
        bybit = "Trade"
    ),
    Commission(
        binance = "",
        bybit = ""
    ),
    Funding(
        binance = "",
        bybit = "Funding"
    );
    companion object {
        fun binance(value: String): ExecType {
            return values().first { it.binance == value }
        }
        fun bybit(value: String): ExecType {
            return values().first { it.bybit == value }
        }
    }
}
