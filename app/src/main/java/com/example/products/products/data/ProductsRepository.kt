package com.example.products.products.data

import com.example.products.products.data.model.Product

interface ProductsRepository {
    suspend fun fetchProducts() :  Result<List<Product>>
}
