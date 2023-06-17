package com.example.products.data.repository

import com.example.products.data.model.Product

interface ProductsRepository {
    suspend fun fetchProducts() :  Result<List<Product>>
}
