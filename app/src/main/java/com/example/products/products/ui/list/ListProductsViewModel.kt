package com.example.products.products.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.products.products.data.ProductsRepository
import com.example.products.products.data.model.Product
import com.example.products.products.domain.CalculateTotalPriceUseCase
import com.example.products.products.ui.list.state.ListScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListProductsViewModel @Inject constructor(
    private val productsRepository: ProductsRepository,
    private val calculateTotalPriceUseCase: CalculateTotalPriceUseCase
): ViewModel() {

    private val _state = MutableStateFlow(ListScreenState())
    val state = _state.asStateFlow()

    private var listProducts : List<Product> = emptyList()

    private val mapProducts = mutableMapOf<String, Int>()

    init {
        fetchProducts()
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            showLoading()
            productsRepository.fetchProducts()
                .onSuccess { products ->
                    listProducts = products
                    updateListState()
                }.onFailure {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = "There were an error" //TODO move to the string resources
                        )
                    }
                }
        }
    }

    fun onAddItem(code: String) {
        val quantity = mapProducts[code]?.let { it + 1 } ?: 1
        mapProducts[code] = quantity
        updateListState()
    }

    fun onRemoveItem(code : String) {
        mapProducts[code]?.let {
            if(it != 0) {
                mapProducts[code] = it - 1
            }
        }

        updateListState()
    }

    private fun List<Product>.updateQuantities() = this.map { it.copy(quantity = mapProducts[it.code] ?: 0) }
    //private fun List<Product>.calculateTotalPrice(): Double = sumOf { it.price * it.quantity }
    private fun updateListState() {
        val updatedProducts = listProducts.updateQuantities()
        _state.update {
            it.copy(
                isLoading = false,
                products = updatedProducts,
                totalPrice = calculateTotalPriceUseCase.invoke(updatedProducts)
            )
        }
    }

    private fun showLoading() {
        _state.update {
            it.copy(
                isLoading = true
            )
        }
    }
}
