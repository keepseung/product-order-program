package kr.co._29cm.homework.config.converter

fun interface Converter<S, T> {
    fun convert(source: S): T
}
