package com.example.products.products.ui.list

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.products.R
import com.example.products.products.data.extensions.formatPriceAsEuro

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
    onNavigateToCheckout: ()-> Unit,
    onAddItem: (String) -> Unit,
    onRemoveItem: (String) -> Unit,
) {
    val lazyListState = rememberLazyListState()

    LazyColumn(
        Modifier.fillMaxSize(),
        state = lazyListState

    ) {
        item {
            Title()
        }
        items(products) {
            CardContent(product = it, onAddItem, onRemoveItem)
        }
        item {
            checkoutButton(totalPrice, onNavigateToCheckout)
        }
    }
}

@Composable
private fun Title(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.cabify_shop),
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.ExtraBold),
            modifier = Modifier.fillMaxWidth()
        )
    }
}
@Composable
private fun checkoutButton(
    totalPrice: Double,
    onNavigateToCheckout: ()->Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(onClick = onNavigateToCheckout) {
            Text(
                text = stringResource(R.string.total_price, totalPrice.formatPriceAsEuro())
            )
        }
    }
}

@Composable
private fun CardContent(
    product: Product,
    onAddItem: (String) -> Unit,
    onRemoveItem: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            content = {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold
                    )
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    content = {
                        Text(text = product.price.formatPriceAsEuro())
                        QuantityButtonRow(
                            quantity = product.quantity,
                            onAddItem = { onAddItem(product.code) },
                            onRemoveItem = { onRemoveItem(product.code) }
                        )
                    }
                )

                product.discount?.let { discount ->
                    Text(text = discount)
                }
            }
        )
    }
}

@Composable
private fun QuantityButtonRow(
    quantity: Int,
    onAddItem: () -> Unit,
    onRemoveItem: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(
            onClick = onRemoveItem,
            enabled = quantity > 0
        ) {
            Icon(
                painter = painterResource(R.drawable.remove),
                contentDescription = "Minus"
            )
        }
        Text(
            text = quantity.toString(),
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        IconButton(
            onClick = onAddItem
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Plus"
            )
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
