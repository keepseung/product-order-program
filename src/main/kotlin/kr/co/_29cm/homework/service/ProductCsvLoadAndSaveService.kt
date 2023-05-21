package kr.co._29cm.homework.service

import kr.co._29cm.homework.domain.Money
import kr.co._29cm.homework.domain.ProductStored
import kr.co._29cm.homework.repository.ProductRepository
import java.io.File

class ProductCsvLoadAndSaveService(
    private val productRepository: ProductRepository,
) {

    fun saveProducts() {
        productRepository.saveAll(loadProductsFromCsv(fileName))
    }

    private fun loadProductsFromCsv(csvFilePath: String): List<ProductStored> {
        val fire = File(csvFilePath)
        val reader = fire.inputStream().bufferedReader()
        reader.readLine()
        return reader.lineSequence()
            .filter { it.isNotBlank() }
            .map { line ->
                val values = parseCsvLine(line)
                val productNumber = values[0]
                val productName = values[1]
                val price = values[2].toInt()
                val availableStock = values[3].toInt()

                ProductStored(
                    productNumber = productNumber.toInt(),
                    productName = productName,
                    price = Money(price),
                    availableStock = availableStock
                )
            }
            .toList()
    }

    /**
     * 상품명이 쌍따옴표 안에 콤마 ,가 있는 경우 ""를 제외한 상품명을 가져오기 위한 처리를 진행함
     */
    private fun parseCsvLine(line: String): List<String> {
        val values = mutableListOf<String>()
        var inQuotes = false
        var currentValue = StringBuilder()

        for (c in line) {
            when {
                c == ',' && !inQuotes -> {
                    values.add(currentValue.toString().trim())
                    currentValue = StringBuilder()
                }

                c == '"' -> inQuotes = !inQuotes
                else -> currentValue.append(c)
            }
        }
        values.add(currentValue.toString().trim())

        return values
    }

    companion object {
        private const val fileName = "src/main/resources/products.csv"
    }
}
