package kr.co._29cm.homework.domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import kr.co._29cm.homework.exception.SoldOutException
import org.junit.jupiter.api.Test

class ProductStoredTest {
    @Test
    fun `재고와 같거나 적게 주문한 경우 재고가 감소한다 `() {
        // given
        val productStored = ProductStored(
            productNumber = 1,
            productName = "아이폰",
            price = Money(30000),
            availableStock = 1000
        )

        // when
        val quantity = 100
        productStored.minusStock(quantity)

        // then
        productStored.availableStock shouldBe 900
    }

    @Test
    fun `재고보다 많이 주문한 경우 예외가 발생한다`() {
        // given
        val productStored = ProductStored(
            productNumber = 1,
            productName = "아이폰",
            price = Money(30000),
            availableStock = 1000
        )

        // when, then
        val quantity = 2000
        shouldThrow<SoldOutException> {
            productStored.minusStock(quantity)
        }
    }
}
