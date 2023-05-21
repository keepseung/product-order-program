package kr.co._29cm.homework.config.validator

import io.kotest.assertions.throwables.shouldThrow
import org.assertj.core.api.Assertions.assertThatCode
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class ProgressValidatorTest {
    val progressValidator = ProgressValidator()

    @ParameterizedTest
    @ValueSource(strings = ["o", "q"])
    fun `주어진 값이 주어진 경우, 검증 에러가 발생하지 않는다`(target: String) {
        assertThatCode { progressValidator.validate(target) }.doesNotThrowAnyException()
    }

    @Test
    fun `o 또는 p 이외의 값이 주어진 경우, 검증 에러가 발생한다`() {
        shouldThrow<IllegalArgumentException> {
            progressValidator.validate("oaaaa")
        }
    }
}
