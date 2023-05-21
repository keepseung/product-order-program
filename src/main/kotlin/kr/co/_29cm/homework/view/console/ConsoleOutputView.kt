package kr.co._29cm.homework.view.console

import kr.co._29cm.homework.domain.Order
import kr.co._29cm.homework.domain.OrderLine
import kr.co._29cm.homework.domain.ProductStored
import kr.co._29cm.homework.util.formatComma
import kr.co._29cm.homework.view.OutputView

class ConsoleOutputView : OutputView {
    override fun printOrderResult(order: Order) {
        printLine()
        printOrderLines(order.orderLines)
        printLine()
        printAmountsInfo(order)
        printLine()
        println()
    }

    override fun printProducts(productEntities: List<ProductStored>) {
        if (productEntities.isEmpty()) return

        print("상품번호")
        repeat((1..10).count()) { print(SPACE_ONE) }
        print("상품명")
        repeat((1..40).count()) { print(SPACE_ONE) }
        print("판매가격")
        repeat((1..10).count()) { print(SPACE_ONE) }
        println("재고수")

        productEntities.forEach {
            print(it.productNumber)
            repeat((1..10).count()) { print(SPACE_ONE) }
            print(it.productName)
            repeat((1..10).count()) { print(SPACE_ONE) }
            print("${it.price.value.formatComma()}원")
            repeat((1..10).count()) { print(SPACE_ONE) }
            println(it.availableStock)
        }
    }

    private fun printAmountsInfo(order: Order) {
        println("주문금액: ${order.getOrderAmounts().value.formatComma()}원")
        val deliveryFee = order.getDeliveryFee().value
        if (deliveryFee > 0) {
            println("배송비: ${deliveryFee.formatComma()}원")
        }
        printLine()
        println("지불금액: ${order.getPayAmounts().value.formatComma()}원")
    }

    private fun printOrderLines(orderLines: List<OrderLine>) {
        orderLines.forEach { orderLine ->
            println("${orderLine.product.productName} - ${orderLine.quantity}개")
        }
    }

    override fun printQuitMessage() {
        println(QUIT_MESSAGE)
    }

    override fun printSoldOutException() {
        println(SOLD_OUT_MESSAGE)
    }

    private fun printLine() {
        println(LINE)
    }

    companion object {
        private const val LINE = "--------------------------------"
        private const val QUIT_MESSAGE = "고객님의 주문 감사합니다."
        private const val SOLD_OUT_MESSAGE = "SoldOutException 발생. 주문한 상품량이 재고량보다 큽니다."
        private const val SPACE_ONE = " "
    }
}
