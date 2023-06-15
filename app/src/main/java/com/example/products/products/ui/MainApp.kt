package com.example.products.products.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import com.example.products.products.ui.checkout.CheckoutScreen
import com.example.products.products.ui.list.ListProductsScreen
import com.example.products.ui.theme.ProductsTheme

@Composable
fun MainApp() {
    val navController = rememberNavController()
    ProductsTheme {
        NavHost(navController = navController, startDestination = "list") {
            composable("list") {
                ListProductsScreen() {
                    navController.navigate("checkout")
                }
            }
            composable(
                route = "checkout"
            ) {
                CheckoutScreen()
            }
        }
    }
}
