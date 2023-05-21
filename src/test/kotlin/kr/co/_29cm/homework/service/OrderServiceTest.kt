package kr.co._29cm.homework.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kr.co._29cm.homework.domain.Money
import kr.co._29cm.homework.exception.SoldOutException
import kr.co._29cm.homework.repository.Datasource
import kr.co._29cm.homework.repository.InMemoryOrderLineRepository
import kr.co._29cm.homework.repository.InMemoryProductRepository
import kr.co._29cm.homework.repository.ProductRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

class OrderServiceTest {
    private val productRepository: ProductRepository = InMemoryProductRepository()
    private val productService = ProductService(productRepository)
    private val orderLineRepository = InMemoryOrderLineRepository()
    private val productCsvLoadAndSaveService = ProductCsvLoadAndSaveService(productRepository)

    private val orderService = OrderService(orderLineRepository, productService)

    @BeforeEach
    fun initData() {
        productRepository.clear()
        orderLineRepository.clear()
        productCsvLoadAndSaveService.saveProducts()
    }

    @Nested
    @DisplayName("상품 선택 테스트")
    inner class AddOrderLineTest {
        @Test
        fun `상품을 처음 선택하는 경우, 상품 및 개수 저장하는데 성공한다`() {
            // given
            val productNumber = 779049
            val quantity = 10

            // when
            orderService.addOrderLine(productNumber, quantity)

            // then
            val orderLine = Datasource.orderLineMap[productNumber]
            orderLine shouldNotBe null
            orderLine!!.product.productNumber shouldBe productNumber
            orderLine.quantity shouldBe quantity
        }

        @Test
        fun `이전에 상품을 선택했고, 같은 상품을 선택하는 경우 상품 개수가 더해진다`() {
            // given
            val productNumber = 779049
            val firstQuantity = 10
            orderService.addOrderLine(productNumber, firstQuantity)

            // when
            val secondQuantity = 20
            orderService.addOrderLine(productNumber, secondQuantity)
            val totalQuantity = firstQuantity + secondQuantity

            // then
            val orderLine = orderLineRepository.findByProductNumber(productNumber)
            orderLine shouldNotBe null
            orderLine!!.product.productNumber shouldBe productNumber
            orderLine.quantity shouldBe totalQuantity
            orderLine.amounts shouldBe Money(orderLine.product.price.value * totalQuantity)
        }

        @Test
        fun `존재하지 않는 상품이라면, 상품 선택에 실패한다`() {
            // given
            val productNumber = 123
            val quantity = 10

            // when, then
            shouldThrow<IllegalArgumentException> {
                orderService.addOrderLine(productNumber, quantity)
            }
        }

        @Test
        fun `0개 이하로 선택한 경우, 상품 선택에 실패한다`() {
            // given
            val productNumber = 779049
            val quantity = 0

            // when, then
            shouldThrow<IllegalArgumentException> {
                orderService.addOrderLine(productNumber, quantity)
            }
        }
    }

    @Nested
    @DisplayName("주문 생성 테스트")
    inner class GenerateOrderTest {
        @Test
        fun `재고가 충분하다면, 주문 생성에 성공하고 재고가 감소한다`() {
            // given
            val productNumber = 779049
            val quantity = 10
            val availableStock = productService.findByProductNumber(productNumber).availableStock
            val remainStock = availableStock - 10
            orderService.addOrderLine(productNumber, quantity)

            // when
            val order = orderService.generateOrder()

            // then
            order shouldNotBe null
            order.orderLines.size shouldBe 1
            productService.findByProductNumber(productNumber).availableStock shouldBe remainStock
        }

        @Test
        fun `재고가 부족하다면, 주문 생성에 실패한다`() {
            // given
            val productNumber = 779049
            val quantity = 4500
            orderService.addOrderLine(productNumber, quantity)

            // when, then
            shouldThrow<SoldOutException> {
                orderService.generateOrder()
            }
        }

        @Test
        fun `멀티 쓰레드 환경에서 재고가 부족하다면, 주문 생성에 실패한다`() {
            // given
            // 총 수량이 64이다.
            val productNumber = 779049
            val availableStock = productService.findByProductNumber(productNumber).availableStock
            val quantity = 30
            val remainStock = availableStock - 30 * 2
            orderService.addOrderLine(productNumber, quantity)

            val service = Executors.newFixedThreadPool(3)

            val latch = CountDownLatch(3)

            // when, then
            service.execute {
                orderService.generateOrder()
                latch.countDown()
            }
            service.execute {
                orderService.generateOrder()
                latch.countDown()
            }
            service.execute {
                shouldThrow<SoldOutException> {
                    orderService.generateOrder()
                }
                latch.countDown()
            }
            latch.await()

            productService.findByProductNumber(productNumber).availableStock shouldBe remainStock
        }
    }
}
