package kr.co._29cm.homework.domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.assertj.core.api.Assertions.assertThatCode
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class OrderTest {
    @ParameterizedTest(name = "{1}")
    @MethodSource("validOrderLines")
    fun `주문은 여러 상품으로 구성된다`(orderLines: List<OrderLine>, name: String) {
        assertThatCode { Order(orderLines) }.doesNotThrowAnyException()
    }

    @ParameterizedTest(name = "{2}")
    @MethodSource("orderLinesForDeliveryFee")
    fun `주문 금액이 5만원 미만인 경우 배송료 2,500원이 추가되고, 5만원 이상인 경우 배송료가 무료이다`(
        orderLines: List<OrderLine>,
        result: Int,
        name: String,
    ) {
        val order = Order(orderLines)
        order.getDeliveryFee() shouldBe Money(result)
    }

    @Test
    fun `주문 금액은 상품별 결제 금액을 모두 더한 것이다`() {
        val expectedOrderAmounts = 9000
        val order = Order(listOf(oneOrderLine, twoOrderLine))
        order.getOrderAmounts() shouldBe Money(expectedOrderAmounts)
    }

    @Test
    fun `지불 금액은 주문 금액과 배송료를 더한 금액이다`() {
        val expectedPayAmounts = 11500
        val order = Order(listOf(oneOrderLine, twoOrderLine))
        order.getPayAmounts() shouldBe Money(expectedPayAmounts)
    }

    @ParameterizedTest(name = "{2}")
    @MethodSource("invalidOrderLines")
    fun `주문 생성 유효성 검사`(orderLines: List<OrderLine>, exceptionMessage: String, name: String) {
        val illegalArgumentException = shouldThrow<IllegalArgumentException> { Order(orderLines) }
        illegalArgumentException.message shouldBe exceptionMessage
    }

    companion object {
        @JvmStatic
        fun validOrderLines(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    listOf(oneOrderLine),
                    "1개의 상품으로 주문 생성이 가능하다"
                ),
                Arguments.of(
                    listOf(oneOrderLine, twoOrderLine),
                    "2개 이상의 상품들로 주문 생성이 가능하다"
                )
            )
        }

        @JvmStatic
        fun orderLinesForDeliveryFee(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    listOf(
                        OrderLine(
                            Product(
                                productNumber = 1,
                                productName = "아이폰",
                                price = Money(30000),
                            ),
                            2,
                        )
                    ),
                    Order.FREE_DELIVERY_FEE,
                    "주문 금액이 5만원 이상인 경우 배송료가 무료이다"
                ),
                Arguments.of(
                    listOf(oneOrderLine),
                    Order.DEFAULT_DELIVERY_FEE,
                    "주문 금액이 5만원 미만인 경우 배송료 2,500원이다"
                )
            )
        }

        @JvmStatic
        fun invalidOrderLines(): Stream<Arguments> = Stream.of(
            Arguments.of(
                listOf<OrderLine>(),
                Order.EXCEPTION_MESSAGE_ORDER_EMPTY,
                "상품이 없는 경우 주문 생성이 안된다"
            ),
            Arguments.of(
                listOf(oneOrderLine, oneOrderLine),
                Order.EXCEPTION_MESSAGE_DUPLICATE_PRODUCT,
                "주문에 속한 상품 중에 중복된 상품이 있는 경우 주문 생성이 안된다"
            )
        )

        private val oneOrderLine = OrderLine(
            Product(
                productNumber = 1,
                productName = "아이폰",
                price = Money(5000),
            ),
            1,
        )

        private val twoOrderLine = OrderLine(
            Product(
                productNumber = 2,
                productName = "맥북",
                price = Money(2000),
            ),
            2,
        )
    }
}
