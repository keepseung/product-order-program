package kr.co._29cm.homework.service

import kr.co._29cm.homework.domain.Order
import kr.co._29cm.homework.domain.OrderLine
import kr.co._29cm.homework.repository.OrderLineRepository

class OrderService(
    private val orderLineRepository: OrderLineRepository,
    private val productService: ProductService,
) {
    fun addOrderLine(inputProductNumber: Int, inputQuantity: Int) {
        val productEntity = productService.findByProductNumber(inputProductNumber)
        when (val orderLine = orderLineRepository.findByProductNumber(inputProductNumber)) {
            null -> {
                orderLineRepository.insert(
                    OrderLine(
                        product = productEntity.toProduct(),
                        quantity = inputQuantity
                    )
                )
            }

            else -> {
                orderLine.plusQuantity(inputQuantity)
                orderLineRepository.update(orderLine)
            }
        }
    }

    fun generateOrder(): Order {
        val orderLines = orderLineRepository.findAllOrderLines()
        orderLines.forEach { orderLine ->
            productService.minusStock(orderLine.product.productNumber, orderLine.quantity)
        }
        return Order(orderLines)
    }

    fun clear() {
        orderLineRepository.clear()
    }
}
