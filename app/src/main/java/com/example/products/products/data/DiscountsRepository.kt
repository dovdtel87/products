package com.example.products.products.data

import com.example.products.products.data.model.Discount

interface DiscountsRepository {

    fun getDiscountForProduct(code: String) : Discount?
}
