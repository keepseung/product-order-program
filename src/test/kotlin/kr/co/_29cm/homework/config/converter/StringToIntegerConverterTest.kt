package kr.co._29cm.homework.config.converter

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class StringToIntegerConverterTest {
    val stringToIntegerConverter = StringToIntegerConverter()

    @Test
    fun `정수로 이루어진 문자열이 주어진다면, Int 타입으로 형변환된다`() {
        val result = stringToIntegerConverter.convert("1000")
        result shouldBe 1000
    }

    @Test
    fun `정수로 이루어지지 않은 문자열이 주어진다면, Int 타입으로 형변환 실패한다`() {
        shouldThrow<IllegalArgumentException> {
            stringToIntegerConverter.convert("1000dd")
        }
    }
}
