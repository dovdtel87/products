package com.example.products.data.repository

import com.example.products.data.model.Discount

interface DiscountsRepository {

    fun getDiscountForProduct(code: String) : Discount?
}
