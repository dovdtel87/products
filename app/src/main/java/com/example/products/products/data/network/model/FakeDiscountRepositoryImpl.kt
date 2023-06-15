package com.example.products.products.data.network.model

import com.example.products.products.data.repository.DiscountsRepository
import com.example.products.products.data.model.Discount
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
