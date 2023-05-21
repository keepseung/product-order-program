package kr.co._29cm.homework.util

fun String.isNumeric(): Boolean {
    return this.chars().allMatch { Character.isDigit(it) }
}
