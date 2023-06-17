package com.example.products.products.domain.model

import junit.framework.TestCase.assertEquals
import org.junit.Test

class ProductUITest {
    @Test
    fun `productUI properties should have correct values`() {
        val code = "p1"
        val name = "Product 1"
        val price = 10.0
        val quantity = 2
        val discount = "20% off"
        val message = "Limited stock"

        val productUI = ProductUI(code, name, price, quantity, discount, message)

        assertEquals(code, productUI.code)
        assertEquals(name, productUI.name)
        assertEquals(price, productUI.price)
        assertEquals(quantity, productUI.quantity)
        assertEquals(discount, productUI.discount)
        assertEquals(message, productUI.message)
    }
}
