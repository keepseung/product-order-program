package kr.co._29cm.homework.repository

import kr.co._29cm.homework.domain.OrderLine

interface OrderLineRepository {
    fun insert(orderLine: OrderLine)
    fun update(orderLine: OrderLine)
    fun clear()
    fun findAllOrderLines(): List<OrderLine>
    fun findByProductNumber(productNumber: Int): OrderLine?
}
