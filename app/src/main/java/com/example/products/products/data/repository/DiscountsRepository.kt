package com.example.products.products.data.repository

import com.example.products.products.data.model.Discount

interface DiscountsRepository {

    fun getDiscountForProduct(code: String) : Discount?
}
