package kr.co._29cm.homework.config.validator

import io.kotest.assertions.throwables.shouldThrow
import org.assertj.core.api.Assertions.assertThatCode
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class OrderInfoValidatorTest {
    val orderInfoValidator = OrderInfoValidator()

    @ParameterizedTest
    @ValueSource(strings = [" ", "487587"])
    fun `숫자 또는 띄어쓰기 한 번 이외의 문자열 값이 주어진 경우, 검증 에러가 발생하지 않는다`(target: String) {
        assertThatCode { orderInfoValidator.validate(target) }.doesNotThrowAnyException()
    }

    @Test
    fun `숫자 또는 띄어쓰기 한 번 이외의 문자열 값이 주여진 경우, 검증 에러가 발생한다`() {
        val target = "11ddd"
        shouldThrow<IllegalArgumentException> {
            orderInfoValidator.validate(target)
        }
    }
}
