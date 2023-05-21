package kr.co._29cm.homework.config.validator

class ProgressValidator : Validator {
    override fun validate(target: String) {
        require(ALLOWED_VALUES.contains(target)) {
            "${ALLOWED_VALUES.joinToString()} 값만 입력할 수 있습니다."
        }
    }

    companion object {
        const val ORDER_INPUT = "o"
        const val QUIT_INPUT = "q"
        private val ALLOWED_VALUES = listOf(
            ORDER_INPUT,
            QUIT_INPUT,
        )
    }
}
