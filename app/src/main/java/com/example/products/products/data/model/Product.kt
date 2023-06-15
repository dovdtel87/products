package com.example.products.products.data.model

data class Product(
    val code: String,
    val name: String,
    val price: Double,
    var quantity: Int = 0,
    var discount: String? = null
)
