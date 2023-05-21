package kr.co._29cm.homework.view

import kr.co._29cm.homework.domain.Order
import kr.co._29cm.homework.domain.ProductStored

interface OutputView {
    fun printOrderResult(order: Order)
    fun printProducts(productEntities: List<ProductStored>)
    fun printQuitMessage()
    fun printSoldOutException()
}
