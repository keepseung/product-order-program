package kr.co._29cm.homework.domain

import io.kotest.assertions.throwables.shouldThrow
import org.assertj.core.api.Assertions.assertThatCode
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class MoneyTest {
    @ParameterizedTest
    @ValueSource(ints = [0, 1000])
    fun `돈은 0 이상인 값으로 구성된다`(value: Int) {
        assertThatCode { Money(value) }.doesNotThrowAnyException()
    }

    @Test
    fun `값이 0 미만인 경우 돈이 생성되지 않는다`() {
        shouldThrow<IllegalArgumentException> { Money(-100) }
    }
}
