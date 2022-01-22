package tax

import model.Asset

data class Wallet(
    val holdings: Map<Asset, Holding>
) {
    fun replace(asset: Asset, newHolding: Holding): Wallet {
        return copy(
            holdings = holdings.mapValues {
                if (it.key == asset) {
                    newHolding
                } else {
                    it.value
                }
            }
        )
    }
}
