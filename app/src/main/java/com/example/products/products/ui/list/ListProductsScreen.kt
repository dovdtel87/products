package com.example.products.products.ui.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.products.ui.theme.ProductsTheme

@Composable
fun ListProductsScreen(
    viewModel: ListProductsViewModel,
    onNavigateToCheckout: () -> Unit
) {
    ProductsTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column() {
                Text(
                    text = "Products screen"
                )
                Button(onClick = onNavigateToCheckout) {
                    Text(
                        text = "Navigate to checkout"
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListProductsScreenPreview() {
    ListProductsScreen(viewModel() ,{})
}
