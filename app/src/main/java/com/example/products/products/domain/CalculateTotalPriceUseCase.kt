package com.example.products.products.domain

import com.example.products.products.data.DiscountsRepository
import com.example.products.products.data.model.Discount
import com.example.products.products.data.model.Product
import javax.inject.Inject

class CalculateTotalPriceUseCase @Inject constructor(
    private val discountsRepository : DiscountsRepository
) {

    fun invoke(products: List<Product>) : Double {
        var total = 0.0
        products.forEach { product ->
            val discount = discountsRepository.getDiscountForProduct(product.code)
            discount?.let {
                if (discount is Discount.FreeItem) {
                    val itemsFree: Int = (product.quantity/discount.numberToBuy)*discount.numberFree
                    total += (product.quantity - itemsFree) * product.price
                }

                if (discount is Discount.PriceReduction) {
                    val finalPrice = if(product.quantity >= discount.numberToBuy) {
                        product.price - discount.directDiscount
                    } else {
                        product.price
                    }
                    total += finalPrice * product.quantity
                }
            } ?: kotlin.run {
                total += product.quantity * product.price
            }
        }
        return total
    }
}
