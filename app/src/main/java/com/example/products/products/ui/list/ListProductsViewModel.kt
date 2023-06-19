package com.example.products.products.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.products.R
import com.example.products.data.model.Product
import com.example.products.products.domain.usecase.UpdateProductsUIUseCase
import com.example.products.products.domain.usecase.FetchProductsUseCase
import com.example.products.products.domain.model.ProductUI
import com.example.products.products.ui.list.state.ListScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListProductsViewModel @Inject constructor(
    private val fetchProductsUseCase: FetchProductsUseCase,
    private val updateProductsUIUseCase: UpdateProductsUIUseCase,
): ViewModel() {
    
    private val _state = MutableStateFlow<ListScreenState>(ListScreenState.Loading)
    val state = _state.asStateFlow()

    private var listProducts : List<Product> = emptyList()
    private val mapQuantities = mutableMapOf<String, Int>()

    init {
        fetchProducts()
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            showLoading()
            fetchProductsUseCase.invoke()
                .onSuccess { products ->
                    listProducts = products
                    updateListState()
                }.onFailure {
                    _state.update {
                        ListScreenState.Error(R.string.error)
                    }
                }
        }
    }

    fun onAddItem(code: String) {
        val quantity = mapQuantities[code]?.let { it + 1 } ?: 1
        mapQuantities[code] = quantity
        updateListState()
    }

    fun onRemoveItem(code : String) {
        mapQuantities[code]?.let {
            if(it != 0) {
                mapQuantities[code] = it - 1
            }
        }
        updateListState()
    }

    private fun updateListState() {
        val uiElements : Pair<List<ProductUI>, Double> = updateProductsUIUseCase.invoke(listProducts, mapQuantities)
        _state.update {
            ListScreenState.Content(uiElements.first, uiElements.second)
        }
    }

    private fun showLoading() {
        _state.update {
            ListScreenState.Loading
        }
    }
}
