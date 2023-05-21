package kr.co._29cm.homework.config.validator

import kr.co._29cm.homework.util.isNumeric

class OrderInfoValidator : Validator {
    override fun validate(target: String) {
        require(ALLOWED_VALUES.contains(target) || target.isNumeric()) {
            "문자 또는 공백 문자만 입력할 수 있습니다."
        }
    }

    companion object {
        const val ORDER_COMPLETE = " "
        private val ALLOWED_VALUES = listOf(
            ORDER_COMPLETE
        )
    }
}
