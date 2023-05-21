package kr.co._29cm.homework.exception

import java.lang.RuntimeException

class SoldOutException(override val message: String?) : RuntimeException(message)
