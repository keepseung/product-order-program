package kr.co._29cm.homework.repository

import kr.co._29cm.homework.domain.ProductStored
import kr.co._29cm.homework.repository.Datasource.productStoredMap

class InMemoryProductRepository : ProductRepository {

    override fun findAll(): List<ProductStored> = productStoredMap.values.toList()

    override fun minusQuantity(productStored: ProductStored) {
        productStoredMap[productStored.productNumber] = productStored
    }

    override fun saveAll(products: List<ProductStored>) {
        products.forEach { productEntity ->
            productStoredMap[productEntity.productNumber] = productEntity
        }
    }

    override fun findByProductNumber(productNumber: Int): ProductStored? {
        return productStoredMap[productNumber]
    }

    override fun clear() {
        productStoredMap.clear()
    }
}
