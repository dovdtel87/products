package com.example.products.products.domain.repository

import com.example.products.data.model.Discount
import com.example.products.data.repository.DiscountsRepository
import javax.inject.Inject

class FakeDiscountRepositoryImpl @Inject constructor() : DiscountsRepository {

    override fun getDiscountForProduct(code: String): Discount? {
        return when(code) {
            "VOUCHER" -> Discount.FreeItem(2,1)
            "TSHIRT" -> Discount.PriceReduction(3,1.0)
            else -> null
        }
    }
}
