package kr.co._29cm.homework.controller

import kr.co._29cm.homework.config.converter.Converter
import kr.co._29cm.homework.config.validator.OrderInfoValidator.Companion.ORDER_COMPLETE
import kr.co._29cm.homework.config.validator.ProgressValidator.Companion.ORDER_INPUT
import kr.co._29cm.homework.config.validator.ProgressValidator.Companion.QUIT_INPUT
import kr.co._29cm.homework.exception.SoldOutException
import kr.co._29cm.homework.service.OrderService
import kr.co._29cm.homework.service.ProductService
import kr.co._29cm.homework.view.InputView
import kr.co._29cm.homework.view.OutputView

class Controller(
    private val outputView: OutputView,
    private val inputView: InputView,
    private val stringToIntegerConverter: Converter<String, Int>,
    private val orderService: OrderService,
    private val productService: ProductService,
) {

    fun run() {
        val isRun = true
        while (isRun) {
            when (inputView.inputOrderOrQuit()) {
                ORDER_INPUT -> {
                    handleOrder()
                }

                QUIT_INPUT -> {
                    handleQuit()
                    break
                }
            }
        }
    }

    private fun handleOrder() {
        outputView.printProducts(productService.findAll())
        val isProgressOrder = true

        while (isProgressOrder) {
            val inputOrderNumber = inputView.inputOrderNumber()
            val inputQuantity = inputView.inputQuantity()

            when {
                inputOrderNumber == ORDER_COMPLETE || inputQuantity == ORDER_COMPLETE -> {
                    handleOrderComplete()
                    break
                }

                else -> {
                    handleAddOrderLine(
                        inputOrderNumber.let { stringToIntegerConverter.convert(it) },
                        inputQuantity.let { stringToIntegerConverter.convert(it) }
                    )
                }
            }
        }
    }

    private fun handleAddOrderLine(inputOrderNumber: Int, inputQuantity: Int) {
        orderService.addOrderLine(inputOrderNumber, inputQuantity)
    }

    private fun handleQuit() {
        outputView.printQuitMessage()
    }

    private fun handleOrderComplete() {
        runCatching {
            orderService.generateOrder()
        }.onSuccess { order ->
            orderService.clear()
            outputView.printOrderResult(order)
        }.onFailure { exception ->
            orderService.clear()
            if (exception is SoldOutException) {
                outputView.printSoldOutException()
            } else {
                throw exception
            }
        }
    }
}
