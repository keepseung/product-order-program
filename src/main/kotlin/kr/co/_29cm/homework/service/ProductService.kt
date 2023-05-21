package kr.co._29cm.homework.service

import kr.co._29cm.homework.domain.ProductStored
import kr.co._29cm.homework.repository.ProductRepository

class ProductService(
    private val productRepository: ProductRepository,
) {
    fun findAll(): List<ProductStored> = productRepository.findAll()

    fun findByProductNumber(productNumber: Int) = requireNotNull(productRepository.findByProductNumber(productNumber)) {
        "상품 아이디에 해당하는 상품이 존재하지 않습니다. 상품 아이디 : $productNumber"
    }

    fun minusStock(productNumber: Int, quantity: Int) {
        val productEntity = findByProductNumber(productNumber)
        productEntity.minusStock(quantity)
        productRepository.minusQuantity(productEntity)
    }
}
