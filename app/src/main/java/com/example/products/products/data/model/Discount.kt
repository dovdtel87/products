package com.example.products.products.data.model

sealed class Discount(
    val numberToBuy: Int,
    val numberFree: Int = 0,
    val directDiscount: Double = 0.0
) {
    class FreeItem(numberToBuy: Int, numberFree: Int) : Discount(numberToBuy, numberFree)
    class PriceReduction(numberToBuy: Int, directDiscount: Double): Discount(numberToBuy, directDiscount = directDiscount)
}
