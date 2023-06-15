package com.example.products.products.domain

import com.example.products.products.data.DiscountsRepository
import com.example.products.products.data.ProductsRepository
import com.example.products.products.data.model.Product
import javax.inject.Inject

class FetchProductsUseCase @Inject constructor(
    private val productsRepository: ProductsRepository,
    private val discountsRepository: DiscountsRepository
) {

    suspend fun  invoke() : Result<List<Product>> {
        return try {
            val products = productsRepository.fetchProducts()
                .onSuccess {
                    updateDiscountMessage(it)
                }
            return Result.success(products.getOrDefault(emptyList()))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun updateDiscountMessage(products: List<Product>) {
         return products.forEach {
            val discount = discountsRepository.getDiscountForProduct(it.code)
            it.discount = discount?.message
        }
    }
}
