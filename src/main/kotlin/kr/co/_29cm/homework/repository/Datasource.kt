package kr.co._29cm.homework.repository

import kr.co._29cm.homework.domain.OrderLine
import kr.co._29cm.homework.domain.ProductStored
import java.util.concurrent.ConcurrentHashMap

object Datasource {
    val productStoredMap: ConcurrentHashMap<Int, ProductStored> = ConcurrentHashMap()
    val orderLineMap: ConcurrentHashMap<Int, OrderLine> = ConcurrentHashMap()
}
