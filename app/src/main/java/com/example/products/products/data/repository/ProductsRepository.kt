package com.example.products.products.data.repository

import com.example.products.products.data.model.Product

interface ProductsRepository {
    suspend fun fetchProducts() :  Result<List<Product>>
}
