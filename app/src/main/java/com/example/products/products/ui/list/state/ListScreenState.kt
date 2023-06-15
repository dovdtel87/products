package com.example.products.products.ui.list.state

import com.example.products.products.data.model.Product

data class ListScreenState(
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList(),
    val error: String = ""
)
