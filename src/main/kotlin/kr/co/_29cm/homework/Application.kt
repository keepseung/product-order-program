package kr.co._29cm.homework

import kr.co._29cm.homework.config.ContainerConfig

fun main() {
    initProductsData()
    ContainerConfig.controller().run()
}

fun initProductsData() {
    ContainerConfig.productCsvLoadAndSaveService().saveProducts()
}
