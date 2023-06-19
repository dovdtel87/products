package com.example.products.repository

import com.example.products.data.model.Product
import com.example.products.data.repository.ProductsRepository

class FakeProductsRepository : ProductsRepository {
    override suspend fun fetchProducts(): Result<List<Product>> {
        val product1 = Product("CHAIR", "Chair", 35.0)
        val product2 = Product("SOFA", "Sofa", 24.0)
        return Result.success(listOf(product1, product2))
    }
}
