package com.example.products.products.domain

import com.example.products.products.data.model.Discount
import com.example.products.products.data.model.Product
import com.example.products.products.data.repository.DiscountsRepository
import com.example.products.products.ui.list.model.ProductUI
import javax.inject.Inject

class UpdateProductsUIUseCase @Inject constructor(
   private val discountsRepository: DiscountsRepository,
   private val calculatePriceUseCase: CalculatePriceWithDiscountUseCase
) {
    fun invoke(products: List<Product>, quantities: Map<String, Int>): Pair<List<ProductUI>, Double>{
        var total = 0.0
        val listProductUI = mutableListOf<ProductUI>()
        products.forEach { product ->
            val quantity = quantities[product.code] ?: 0
            val discount = discountsRepository.getDiscountForProduct(product.code)
            val message = when {
                discount == null -> ""
                ifShouldDisplayFreeItemMessage(quantity, discount) -> {
                    val numberFree = quantity / discount.numberToBuy
                    "You are buying $quantity and getting $numberFree for free!"
                }
                ifShouldDisplayPriceReductionMessage(quantity, discount) -> {
                    "You are buying $quantity and getting ${discount.directDiscount} discount in each!"
                }
                else -> ""
            }

            total += calculatePriceUseCase.invoke(product, quantity)
            val productUI = ProductUI(
                code = product.code,
                name = product.name,
                price = product.price,
                quantity = quantity,
                discount = discount?.message,
                message = message
            )
            listProductUI.add(productUI)
        }
        return Pair(listProductUI, total)
    }

    private fun ifShouldDisplayFreeItemMessage(quantity: Int, discount: Discount?) = quantity > 0 && discount is Discount.FreeItem && quantity >= discount.numberToBuy
    private fun ifShouldDisplayPriceReductionMessage(quantity: Int, discount: Discount?) = quantity > 0 && discount is Discount.PriceReduction && quantity >= discount.numberToBuy
}
