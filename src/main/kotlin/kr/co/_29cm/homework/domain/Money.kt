package kr.co._29cm.homework.domain

import java.util.*

data class Money(
    val value: Int,
) {
    init {
        require(value >= 0) { "돈은 ${MIN_PRICE}원 이상이여야 합니다." }
    }

    fun times(other: Int): Money = Money(value.times(other))

    fun plus(other: Int): Money = Money(value.plus(other))

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val (otherMoney) = other as Money
        return value == otherMoney
    }

    override fun hashCode(): Int {
        return Objects.hash(value)
    }

    companion object {
        private const val MIN_PRICE = 0
    }
}
