package com.example.products.products.ui.list.state

import androidx.annotation.StringRes
import com.example.products.products.domain.model.ProductUI

data class ListScreenState(
    val isLoading: Boolean = false,
    val products: List<ProductUI> = emptyList(),
    val totalPrice: Double = 0.0,
    @StringRes val error: Int? = null
)
