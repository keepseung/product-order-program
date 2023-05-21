package kr.co._29cm.homework.repository

import kr.co._29cm.homework.domain.ProductStored

interface ProductRepository {
    fun findAll(): List<ProductStored>
    fun minusQuantity(productStored: ProductStored)
    fun saveAll(products: List<ProductStored>)
    fun findByProductNumber(productNumber: Int): ProductStored?
    fun clear()
}
