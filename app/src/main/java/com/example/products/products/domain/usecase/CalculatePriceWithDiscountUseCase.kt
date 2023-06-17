package com.example.products.products.domain.usecase

import com.example.products.data.model.Discount
import com.example.products.data.model.Product
import com.example.products.data.repository.DiscountsRepository
import javax.inject.Inject

class CalculatePriceWithDiscountUseCase @Inject constructor(
    private val discountsRepository : DiscountsRepository
) {
    fun invoke(product: Product, quantity: Int): Double {
        return  when (val discount = discountsRepository.getDiscountForProduct(product.code)) {
                is Discount.FreeItem -> calculatePriceForFreeItemDiscount(
                    quantity,
                    discount.numberToBuy,
                    discount.numberFree,
                    product.price
                )
                is Discount.PriceReduction -> calculatePriceForPriceReductionDiscount(
                    quantity,
                    discount.numberToBuy,
                    discount.directDiscount,
                    product.price
                )
                else -> calculatePriceWithOutDiscount(quantity, product.price)
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