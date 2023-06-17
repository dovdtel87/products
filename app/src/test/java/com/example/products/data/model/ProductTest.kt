package com.example.products.data.model

import junit.framework.TestCase.assertEquals
import org.junit.Test

class ProductTest {

    @Test
    fun `product properties should have correct values`() {
        val code = "VOUCHER"
        val name = "Voucher"
        val price = 10.0

        val product = Product(code, name, price)

        assertEquals(code, product.code)
        assertEquals(name, product.name)
        assertEquals(price, product.price)
    }
}
