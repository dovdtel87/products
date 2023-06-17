package com.example.products.products.domain

import com.example.products.products.data.repository.DiscountsRepository
import com.example.products.products.data.model.Discount
import com.example.products.products.ui.list.model.ProductUI
import javax.inject.Inject

class CalculateTotalPriceUseCase @Inject constructor(
    private val discountsRepository : DiscountsRepository
) {

    fun invoke(products: List<ProductUI>): Double {
        return products.sumOf { product ->
            when (val discount = discountsRepository.getDiscountForProduct(product.code)) {
                is Discount.FreeItem -> calculatePriceForFreeItemDiscount(product.quantity, discount.numberToBuy, discount.numberFree, product.price)
                is Discount.PriceReduction -> calculatePriceForPriceReductionDiscount(product.quantity, discount.numberToBuy, discount.directDiscount, product.price)
                else -> calculatePriceWithOutDiscount(product.quantity, product.price)
            }
        }
    }

    private fun calculatePriceForFreeItemDiscount(quantity: Int, numberToBuy: Int, numberFree: Int, price: Double) : Double {
        val itemsFree = (quantity / numberToBuy) * numberFree
        return (quantity - itemsFree) * price
    }

    private fun calculatePriceForPriceReductionDiscount(quantity: Int, numberToBuy: Int, directDiscount: Double, price: Double) : Double {
        val finalPrice = when {
            quantity >= numberToBuy -> price - directDiscount
            else -> price
        }
        return finalPrice * quantity
    }

    private fun calculatePriceWithOutDiscount(quantity: Int, price: Double) = quantity * price
}
