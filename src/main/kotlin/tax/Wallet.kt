package tax

import model.Asset
import java.math.BigDecimal

data class Wallet(
    val holdings: Map<Asset, Holding>
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
}
