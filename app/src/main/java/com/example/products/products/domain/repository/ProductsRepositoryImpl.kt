package com.example.products.products.domain.repository

import com.example.products.data.database.ProductsDao
import com.example.products.data.mapper.ProductMapper
import com.example.products.data.model.Product
import com.example.products.data.network.api.ProductsApi
import com.example.products.data.repository.ProductsRepository
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val productsApi: ProductsApi,
    private val mapper : ProductMapper,
    private val productsDao: ProductsDao
): ProductsRepository {
    override suspend fun fetchProducts(): Result<List<Product>> {
        return try {
            val cachedProducts = mapper.mapToProductFromEntity(productsDao.getProducts())
            if (cachedProducts.isNotEmpty()) {
                Result.success(cachedProducts)
            } else {
                val apiProducts = productsApi.fetchProducts().products
                val mappedProducts = mapper.map(apiProducts)
                productsDao.insertProducts(mapper.mapToProductEntity(mappedProducts))
                Result.success(mappedProducts)
            }
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }
}
