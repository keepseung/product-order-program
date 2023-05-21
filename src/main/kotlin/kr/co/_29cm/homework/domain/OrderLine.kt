package kr.co._29cm.homework.domain

class OrderLine(
    val product: Product,
    var quantity: Int,
) {
    var amounts: Money

    init {
        require(quantity >= MIN_QUANTITY) { "상품은 적어도 ${MIN_QUANTITY}개 선택되야 합니다." }
        amounts = calculateAmounts()
    }

    fun plusQuantity(quantity: Int) {
        this.quantity += quantity
        amounts = calculateAmounts()
    }

    private fun calculateAmounts(): Money = product.price.times(quantity)

    companion object {
        const val MIN_QUANTITY = 1
    }
}
