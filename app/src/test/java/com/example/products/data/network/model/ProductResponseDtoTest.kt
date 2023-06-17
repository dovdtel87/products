package com.example.products.data.network.model

import junit.framework.TestCase.assertEquals
import org.junit.Test

class ProductResponseDtoTest {
    @Test
    fun `productResponseDto should have correct list of products`() {

        val productDto1 = ProductDto("p1", "Product 1", 10.0)
        val productDto2 = ProductDto("p2", "Product 2", 15.0)
        val productDto3 = ProductDto("p3", "Product 3", 20.0)
        val productList = listOf(productDto1, productDto2, productDto3)

        val productResponseDto = ProductResponseDto(productList)

        assertEquals(productList, productResponseDto.products)
    }
}
