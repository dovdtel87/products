package com.example.products.products.ui.list

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.products.products.data.model.Product
import com.example.products.ui.theme.ProductsTheme
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.material.icons.filled.Add

import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person

@Composable
fun ListProductsScreen(
    viewModel: ListProductsViewModel = hiltViewModel(),
    onNavigateToCheckout: () -> Unit
) {
    ProductsTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val uiState = viewModel.state.collectAsStateWithLifecycle().value
            when {
                uiState.isLoading -> { LoadingView() }
                uiState.error.isNotEmpty() -> { ErrorView() }
                else -> {
                    ListProducts(
                        products = uiState.products,
                        totalPrice = uiState.totalPrice,
                        onNavigateToCheckout = onNavigateToCheckout,
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

@Composable
private fun ListProducts(
    products: List<Product>,
    totalPrice: Double,
    onNavigateToCheckout: ()->Unit,
    onAddItem: (String) -> Unit,
    onRemoveItem: (String) -> Unit,
) {
    val lazyListState = rememberLazyListState()

    LazyColumn(
        Modifier.fillMaxSize(),
        state = lazyListState

    ) {
        items(products) {
            CardContent(product = it, onAddItem, onRemoveItem)
        }
        item {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(onClick = onNavigateToCheckout) {
                        Text(
                            text = "Total: $totalPrice" //TODO MOVE STRING TO STRINGS
                        )
                    }
                }
        }
    }
}

@Composable
private fun CardContent(
    product: Product,
    onAddItem: (String) -> Unit,
    onRemoveItem: (String) -> Unit
) {
    val quantityState = remember { mutableStateOf(0) }

    Card(
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold
                    )
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = {
                            quantityState.value = maxOf(quantityState.value - 1, 0) // TODO move this implementation to VM
                            onRemoveItem(product.code)
                      },
                        enabled = quantityState.value > 0
                    ) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "Minus"
                        )
                    }
                    Text(
                        text = quantityState.value.toString(),
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    IconButton(
                        onClick = {
                            quantityState.value += 1 // TODO move this implementation to VM
                            onAddItem(product.code)
                          },
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Plus"
                        )
                    }
                }
            }
            Text(text = product.price.toString())
        }
    }
}

@Composable
fun LoadingView() {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun ErrorView() {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "There were an error loading the list of products",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.displaySmall.copy(fontSize = 30.sp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListProductsScreenPreview() {
    ListProductsScreen(onNavigateToCheckout = {})
}

@Preview(showBackground = true)
@Composable
fun CardContentPreview() {
    CardContent(Product("TSHIRT", "TShirt", 7.5),{},{})
}

@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    LoadingView()
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
    ErrorView()
}
