package com.example.products.products.domain

import com.example.products.products.data.repository.ProductsRepository
import com.example.products.products.data.model.Product
import javax.inject.Inject

class FetchProductsUseCase @Inject constructor(
    private val productsRepository: ProductsRepository
) {
    suspend fun invoke() : Result<List<Product>> {
        return try {
            productsRepository.fetchProducts()
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
