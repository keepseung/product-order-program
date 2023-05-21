package kr.co._29cm.homework.config.converter

class StringToIntegerConverter : Converter<String, Int> {
    override fun convert(source: String): Int =
        runCatching {
            source.toInt()
        }.onFailure {
            throw IllegalArgumentException("숫자만 입력 가능합니다.")
        }.getOrThrow()
}
