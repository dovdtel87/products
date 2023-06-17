package com.example.products.products.ui.list.state

import com.example.products.products.ui.list.model.ProductUI

data class ListScreenState(
    val isLoading: Boolean = false,
    val products: List<ProductUI> = emptyList(),
    val totalPrice: Double = 0.0,
    val error: String = ""
)
