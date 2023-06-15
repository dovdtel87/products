package com.example.products.products.ui.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.products.products.data.ProductsRepository
import com.example.products.products.ui.list.state.ListScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListProductsViewModel @Inject constructor(
    private val productsRepository: ProductsRepository
): ViewModel() {

    private val _state = MutableStateFlow(ListScreenState())
    val state = _state.asStateFlow()

    init {
        fetchProducts()
    }

    fun fetchProducts() {
        Log.d("Test", "Fetching products")

        viewModelScope.launch {
            productsRepository.fetchProducts()
                .onSuccess { products ->
                    products.forEach {
                        Log.d("Test", it.name)
                    }
                    _state.update {
                        it.copy(
                            isLoading = false,
                            products = products
                        )
                    }
                }.onFailure {
                    Log.d("Test", "There where an error")
                }
        }
    }
}
