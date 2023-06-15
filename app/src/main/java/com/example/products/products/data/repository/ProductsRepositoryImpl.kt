package com.example.products.products.data.repository

import com.example.products.products.data.mapper.ProductMapper
import com.example.products.products.data.model.Product
import com.example.products.products.data.network.api.ProductsApi
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val productsApi: ProductsApi,
    private val mapper : ProductMapper
): ProductsRepository {
    override suspend fun fetchProducts(): Result<List<Product>> {
        return try {
            Result.success(mapper.map(productsApi.fetchProducts().products))
        } catch (exception : Exception) {
            Result.failure(exception)
        }
    }
}