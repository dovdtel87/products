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


    private val mapProducts = mutableMapOf<String, Int>()

    init {
        fetchProducts()
    }

    fun fetchProducts() {
        Log.d("Test", "Fetching products")

        viewModelScope.launch {
            showLoading()
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
                    //Todo handle error state
                }
        }
    }

    fun onAddItem(code: String) {
        mapProducts[code]?.let {
            mapProducts[code] = it + 1
        } ?: kotlin.run {
            mapProducts[code] = 1
        }

        Log.d("Test", "Adding item to: "+code+" value: "+mapProducts[code])
    }

    fun onRemoveItem(code : String) {
        mapProducts[code]?.let {
            if(it != 0) {
                mapProducts[code] = it - 1
            }
        }

        Log.d("Test", "Removing item to: "+code+" value: "+mapProducts[code])
    }


    private fun showLoading() {
        _state.update {
            it.copy(
                isLoading = true
            )
        }

    }
}
