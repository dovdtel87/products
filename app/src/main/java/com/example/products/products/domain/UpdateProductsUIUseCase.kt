package com.example.products.products.domain

import com.example.products.products.data.model.Discount
import com.example.products.products.data.model.Product
import com.example.products.products.data.repository.DiscountsRepository
import com.example.products.products.ui.list.model.ProductUI
import javax.inject.Inject

class UpdateProductsUIUseCase @Inject constructor(
   private val discountsRepository: DiscountsRepository
) {
    fun invoke(products: List<Product>, quantities: Map<String,Int>) : List<ProductUI> {
        val listProductUI = mutableListOf<ProductUI>()
        products.forEach { product ->
            val quantity = quantities[product.code] ?: kotlin.run { 0 }
            val discount = discountsRepository.getDiscountForProduct(product.code)
            var message = ""
            if(quantity > 0) {
                discount?.let {
                    if(discount is Discount.FreeItem) {
                        if (quantity >= discount.numberToBuy) {
                            val numberFree = quantity/discount.numberToBuy
                            message = "You are buying $quantity and getting $numberFree for free!"
                        }
                    }
                    if(discount is Discount.PriceReduction) {
                        if (quantity >= discount.numberToBuy) {
                            message = "You are buying "+quantity+" and getting "+discount.directDiscount+" discount in each!"
                        }
                    }
                }
            }

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
        return listProductUI
    }
}
