package kr.co._29cm.homework.domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.assertj.core.api.Assertions.assertThatCode
import org.junit.jupiter.api.Test

class OrderLineTest {
    @Test
    fun `상품이 있고, 개수가 1개 이상인 경우 주문 정보 생성이 가능하다`() {
        val quantity = 10
        assertThatCode { OrderLine(product, quantity) }.doesNotThrowAnyException()
    }

    @Test
    fun `개수가 0 이하인 경우 주문 정보 생성이 안된다`() {
        val quantity = 0
        shouldThrow<IllegalArgumentException> { OrderLine(product, quantity) }
    }

    @Test
    fun `상품별 결제 금액을 상품 가격과 개수를 곱한 값이다`() {
        val expectedAmounts = 1000
        val quantity = 10
        val orderLine = OrderLine(product, quantity)

        orderLine.amounts shouldBe Money(expectedAmounts)
    }

    @Test
    fun `상품 개수가 추가된 경우 상품 개수가 더해지고, 상품 총 가격이 변한다`() {
        val quantity = 10
        val orderLine = OrderLine(product, quantity)

        val plusQuantity = 20
        val totalQuantity = quantity + plusQuantity
        val expectedAmounts = Money(totalQuantity * product.price.value)
        orderLine.plusQuantity(plusQuantity)

        orderLine.amounts shouldBe expectedAmounts
        orderLine.quantity shouldBe totalQuantity
    }

    companion object {
        private val product = Product(1, "아이폰", Money(100))
    }
}
