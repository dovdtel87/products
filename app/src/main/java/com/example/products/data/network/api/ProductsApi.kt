package com.example.products.data.network.api

import com.example.products.data.network.model.ProductResponseDto
import retrofit2.http.GET

interface ProductsApi {

    @GET("Products.json")
    suspend fun fetchProducts() : ProductResponseDto
}
