package kr.co._29cm.homework.domain

data class Order(
    val orderLines: List<OrderLine>,
) {
    init {
        val size = orderLines.size
        require(size > ZERO_ORDER_LINE) { EXCEPTION_MESSAGE_ORDER_EMPTY }
        require(size == orderLines.map { it.product.productNumber }.toSet().size) { EXCEPTION_MESSAGE_DUPLICATE_PRODUCT }
    }

    fun getDeliveryFee(): Money = if (getOrderAmounts().value >= FREE_DELIVERY_FEE_LIMIT) {
        Money(FREE_DELIVERY_FEE)
    } else {
        Money(DEFAULT_DELIVERY_FEE)
    }

    fun getOrderAmounts(): Money = Money(orderLines.sumOf { it.amounts.value })

    fun getPayAmounts(): Money = getOrderAmounts().plus(getDeliveryFee().value)

    companion object {
        const val FREE_DELIVERY_FEE_LIMIT = 50000
        const val FREE_DELIVERY_FEE = 0
        const val DEFAULT_DELIVERY_FEE = 2500
        private const val ZERO_ORDER_LINE = 0
        const val EXCEPTION_MESSAGE_ORDER_EMPTY = "상품을 한 개 이상 선택해야 합니다."
        const val EXCEPTION_MESSAGE_DUPLICATE_PRODUCT = "주문에 속한 여러 상품 중에 중복된 상품이 있으면 안됩니다."
    }
}
