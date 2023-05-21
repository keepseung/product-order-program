package kr.co._29cm.homework.view.console

import kr.co._29cm.homework.config.validator.Validator
import kr.co._29cm.homework.view.InputView

class ConsoleInputView(
    private val orderInfoValidator: Validator,
    private val progressValidator: Validator,
) : InputView {
    override fun inputOrderOrQuit(): String {
        print("입력(o[order]: 주문, q[quit]: 종료) : ")
        return readln().also { progressValidator.validate(it) }
    }

    override fun inputOrderNumber(): String {
        print("상품번호 : ")
        return readln().also { orderInfoValidator.validate(it) }
    }

    override fun inputQuantity(): String {
        print("수량 : ")
        return readln().also { orderInfoValidator.validate(it) }
    }
}
