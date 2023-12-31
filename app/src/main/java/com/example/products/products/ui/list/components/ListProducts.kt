package com.example.products.products.ui.list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.products.R
import com.example.products.extensions.formatPriceAsEuro
import com.example.products.products.domain.model.ProductUI

@Composable
fun ListProducts(
    products: List<ProductUI>,
    totalPrice: Double,
    onAddItem: (String) -> Unit,
    onRemoveItem: (String) -> Unit,
) {
    val lazyListState = rememberLazyListState()

    LazyColumn(
        Modifier.fillMaxSize(),
        state = lazyListState,

        ) {
        item {
            Title()
        }
        items(products) {
            CardContent(product = it, onAddItem, onRemoveItem)
        }
        item {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.total_price, totalPrice.formatPriceAsEuro()),
                    style = MaterialTheme.typography.displaySmall.copy(fontSize = 30.sp),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp)
                )
            }
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
private fun CardContent(
    product: ProductUI,
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
                product.message?.let { message ->
                    Text(text = message)
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

@Preview(showBackground = true)
@Composable
fun CardContentPreview() {
    CardContent(ProductUI("TSHIRT", "TShirt", 7.5),{},{})
}

