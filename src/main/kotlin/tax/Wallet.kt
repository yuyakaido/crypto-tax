package tax

import model.Asset
import java.math.BigDecimal

data class Wallet(
    val holdings: Map<Asset, Holding> = emptyMap()
) {
    fun minus(asset: Asset, amount: BigDecimal): Wallet {
        return copy(
            holdings = holdings.mapValues {
                if (it.key == asset) {
                    it.value.copy(
                        amount = it.value.amount.minus(amount)
                    )
                } else {
                    it.value
                }
            }
        )
    }
    fun addAll(holdings: Map<Asset, Holding>): Wallet {
        return copy(
            holdings = this.holdings.plus(holdings)
        )
    }
}
