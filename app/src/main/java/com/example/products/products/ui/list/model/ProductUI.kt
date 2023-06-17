package com.example.products.products.ui.list.model

data class ProductUI(
    val code: String,
    val name: String,
    val price: Double,
    var quantity: Int = 0,
    var discount: String? = null,
    var message: String? = null
)
