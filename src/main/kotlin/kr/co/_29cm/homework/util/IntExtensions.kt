package kr.co._29cm.homework.util

import java.text.DecimalFormat

fun Int.formatComma(): String {
    val decimalFormat = DecimalFormat("#,###")
    return decimalFormat.format(this)
}
