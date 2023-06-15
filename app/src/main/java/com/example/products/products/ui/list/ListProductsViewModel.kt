package com.example.products.products.ui.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.products.products.data.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListProductsViewModel @Inject constructor(
    private val productsRepository: ProductsRepository
): ViewModel() {
    
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
                }.onFailure {
                    Log.d("Test", "There where an error")
                }
        }
    }
}
