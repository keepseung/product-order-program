package kr.co._29cm.homework.domain

import kr.co._29cm.homework.exception.SoldOutException

data class ProductStored(
    val productNumber: Int,
    val productName: String,
    val price: Money,
    var availableStock: Int,
) {
    fun toProduct(): Product = Product(
        productNumber = this.productNumber,
        productName = this.productName,
        price = this.price,
    )

    fun minusStock(quantity: Int) {
        if (quantity > this.availableStock) {
            throw SoldOutException("주문한 상품량이 재고량보다 큽니다.")
        }
        this.availableStock -= quantity
    }
}
