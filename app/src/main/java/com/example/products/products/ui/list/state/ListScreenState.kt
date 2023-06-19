package com.example.products.products.ui.list.state

import androidx.annotation.StringRes
import com.example.products.products.domain.model.ProductUI
sealed class ListScreenState {
    object Loading : ListScreenState()
    data class Content(val products: List<ProductUI>, val totalPrice: Double) : ListScreenState()
    data class Error(@StringRes val errorMessage: Int) : ListScreenState()
}
