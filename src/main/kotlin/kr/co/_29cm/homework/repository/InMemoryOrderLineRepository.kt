package kr.co._29cm.homework.repository

import kr.co._29cm.homework.domain.OrderLine
import kr.co._29cm.homework.repository.Datasource.orderLineMap

class InMemoryOrderLineRepository : OrderLineRepository {

    override fun insert(orderLine: OrderLine) {
        val product = orderLine.product
        val key = product.productNumber
        val quantity = orderLine.quantity

        orderLineMap[key] = OrderLine(
            product = product,
            quantity = quantity
        )
    }

    override fun update(orderLine: OrderLine) {
        val product = orderLine.product
        val key = product.productNumber
        orderLineMap[key] = orderLine
    }

    override fun clear() {
        orderLineMap.clear()
    }

    override fun findAllOrderLines(): List<OrderLine> {
        return orderLineMap.values.toList()
    }

    override fun findByProductNumber(productNumber: Int): OrderLine? = orderLineMap[productNumber]
}
