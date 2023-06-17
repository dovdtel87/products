package com.example.products.data.model

import com.example.products.extensions.formatPriceAsEuro
import junit.framework.TestCase.assertEquals
import org.junit.Test

class DiscountTest {

    @Test
    fun `FreeItem should have correct properties`() {
        val numberToBuy = 3
        val numberFree = 1
        val message = "Buy $numberToBuy and get $numberFree for free!"

        val discount = Discount.FreeItem(numberToBuy, numberFree)

        assertEquals(numberToBuy, discount.numberToBuy)
        assertEquals(numberFree, discount.numberFree)
        assertEquals(message, discount.message)
    }

    @Test
    fun `PriceReduction should have correct properties`() {
        val numberToBuy = 5
        val directDiscount = 2.0
        val message = "Buy $numberToBuy and get ${directDiscount.formatPriceAsEuro()} discount in each"


        val discount = Discount.PriceReduction(numberToBuy, directDiscount)

        assertEquals(numberToBuy, discount.numberToBuy)
        assertEquals(directDiscount, discount.directDiscount, 0.01)
        assertEquals(message, discount.message)
    }
}