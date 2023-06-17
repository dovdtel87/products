package com.example.products.data.model

import com.example.products.extensions.formatPriceAsEuro

sealed class Discount(
    val numberToBuy: Int,
    val numberFree: Int = 0,
    val directDiscount: Double = 0.0,
    val message: String
) {
    class FreeItem(numberToBuy: Int, numberFree: Int, message: String = "Buy $numberToBuy and get $numberFree for free!") : Discount(numberToBuy, numberFree, message = message)
    class PriceReduction(numberToBuy: Int, directDiscount: Double, message: String = "Buy $numberToBuy and get ${directDiscount.formatPriceAsEuro()} discount in each"): Discount(numberToBuy, directDiscount = directDiscount, message = message)
}
