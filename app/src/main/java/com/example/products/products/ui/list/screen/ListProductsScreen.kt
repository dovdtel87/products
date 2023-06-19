package com.example.products.products.ui.list.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.products.ui.theme.ProductsTheme
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.products.products.ui.list.ListProductsViewModel
import com.example.products.products.ui.list.components.ErrorView
import com.example.products.products.ui.list.components.ListProducts
import com.example.products.products.ui.list.components.LoadingView
import com.example.products.products.ui.list.state.ListScreenState

@Composable
fun ListProductsScreen(
    viewModel: ListProductsViewModel = hiltViewModel(),
) {
    ProductsTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            when(val uiState = viewModel.state.collectAsStateWithLifecycle().value) {
                is ListScreenState.Loading -> { LoadingView() }
                is ListScreenState.Error -> {
                    ErrorView(uiState.errorMessage) {
                        viewModel.fetchProducts()
                    }
                }
                is ListScreenState.Content -> {
                    ListProducts(
                        products = uiState.products,
                        totalPrice = uiState.totalPrice,
                        onAddItem = { code ->
                            viewModel.onAddItem(code)
                        },
                        onRemoveItem = { code ->
                            viewModel.onRemoveItem(code)
                        }
                    )
                }
            }
        }
    }
}
