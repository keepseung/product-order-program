package kr.co._29cm.homework.config

import kr.co._29cm.homework.config.converter.Converter
import kr.co._29cm.homework.config.converter.StringToIntegerConverter
import kr.co._29cm.homework.config.validator.OrderInfoValidator
import kr.co._29cm.homework.config.validator.ProgressValidator
import kr.co._29cm.homework.config.validator.Validator
import kr.co._29cm.homework.controller.Controller
import kr.co._29cm.homework.repository.InMemoryOrderLineRepository
import kr.co._29cm.homework.repository.InMemoryProductRepository
import kr.co._29cm.homework.repository.OrderLineRepository
import kr.co._29cm.homework.repository.ProductRepository
import kr.co._29cm.homework.service.OrderService
import kr.co._29cm.homework.service.ProductCsvLoadAndSaveService
import kr.co._29cm.homework.service.ProductService
import kr.co._29cm.homework.view.InputView
import kr.co._29cm.homework.view.OutputView
import kr.co._29cm.homework.view.console.ConsoleInputView
import kr.co._29cm.homework.view.console.ConsoleOutputView

object ContainerConfig {

    fun inputView(): InputView = ConsoleInputView(
        orderInfoValidator = orderInfoValidator(),
        progressValidator = progressValidator(),
    )

    fun orderLineRepository(): OrderLineRepository = InMemoryOrderLineRepository()

    fun productRepository(): ProductRepository = InMemoryProductRepository()

    fun productService(): ProductService = ProductService(productRepository())

    fun orderService(): OrderService = OrderService(orderLineRepository(), productService())

    fun productCsvLoadAndSaveService(): ProductCsvLoadAndSaveService = ProductCsvLoadAndSaveService(productRepository())

    fun controller(): Controller = Controller(
        outputView = outputView(),
        inputView = inputView(),
        stringToIntegerConverter = stringToIntegerConverter(),
        orderService = orderService(),
        productService = productService(),
    )

    fun progressValidator(): Validator = ProgressValidator()

    fun orderInfoValidator(): Validator = OrderInfoValidator()

    fun stringToIntegerConverter(): Converter<String, Int> = StringToIntegerConverter()

    fun outputView(): OutputView = ConsoleOutputView()
}
