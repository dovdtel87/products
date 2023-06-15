package com.example.products.products.data.network.api

import com.example.products.products.data.network.model.ProductResponseDto
import retrofit2.http.GET

interface ProductsApi {

    //URL https://gist.githubusercontent.com/palcalde/6c19259bd32dd6aafa327fa557859c2f/raw/ba51779474a150ee4367cda4f4ffacdcca479887/Products.json

    @GET("Products.json")
    suspend fun fetchProducts() : ProductResponseDto
}
