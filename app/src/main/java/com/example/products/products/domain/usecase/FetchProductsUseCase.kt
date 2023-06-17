package com.example.products.products.domain.usecase

import com.example.products.data.model.Product
import com.example.products.data.repository.ProductsRepository
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