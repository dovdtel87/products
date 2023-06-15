package com.example.products.products.data.model

sealed class Discount(
    val numberToBuy: Int,
    val numberFree: Int = 0,
    val directDiscount: Double = 0.0,
    val message: String
) {
    class FreeItem(numberToBuy: Int, numberFree: Int, message: String = "Buy $numberToBuy and get {$numberFree} for free!") : Discount(numberToBuy, numberFree, message = message)
    class PriceReduction(numberToBuy: Int, directDiscount: Double, message: String = "Buy $numberToBuy and get {$directDiscount} in each"): Discount(numberToBuy, directDiscount = directDiscount, message = message)
}
