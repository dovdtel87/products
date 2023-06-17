package com.example.products.data.mapper

import com.example.products.data.network.model.ProductDto
import junit.framework.TestCase.assertEquals
import org.junit.Test

class ProductMapperTest {

    private val productMapper = ProductMapper()

    @Test
    fun `map should return the correct list of Product objects`() {
        val productDto1 = ProductDto("p1", "Product 1", 10.0)
        val productDto2 = ProductDto("p2", "Product 2", 15.0)
        val productDto3 = ProductDto("p3", "Product 3", 20.0)
        val productsDto = listOf(productDto1, productDto2, productDto3)

        val productList = productMapper.map(productsDto)

        assertEquals(productsDto.size, productList.size)

        for (i in productsDto.indices) {
            val expectedProductDto = productsDto[i]
            val actualProduct = productList[i]

            assertEquals(expectedProductDto.code, actualProduct.code)
            assertEquals(expectedProductDto.name, actualProduct.name)
            assertEquals(expectedProductDto.price, actualProduct.price)
        }
    }
}