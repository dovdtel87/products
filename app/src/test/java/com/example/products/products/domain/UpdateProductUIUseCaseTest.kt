package com.example.products.products.domain

import com.example.products.products.data.repository.DiscountsRepository
import com.example.products.products.data.model.Discount
import com.example.products.products.data.model.Product
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class UpdateProductUIUseCaseTest {

    private val productVoucher = Product("VOUCHER","Voucher", 5.0)
    private val productTshirt = Product("TSHIRT","Tshirt", 20.0)
    private val productMug = Product("MUG","Mug", 7.50)

    @MockK
    lateinit var discountsRepository: DiscountsRepository

    lateinit var useCase: UpdateProductsUIUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        every { discountsRepository.getDiscountForProduct("MUG") } returns null
        every { discountsRepository.getDiscountForProduct("VOUCHER") } returns Discount.FreeItem(2, 1)
        every { discountsRepository.getDiscountForProduct("TSHIRT") } returns Discount.PriceReduction(3, 1.0)
        useCase = UpdateProductsUIUseCase(discountsRepository, CalculatePriceWithDiscountUseCase(discountsRepository))
    }



    @Test
    fun `one voucher, one tshirt and one mug are 32,50`() {
        val mapQuantities = mutableMapOf<String, Int>("VOUCHER" to 1, "TSHIRT" to 1, "MUG" to 1)
        val result = useCase.invoke(listOf(productVoucher, productTshirt, productMug), mapQuantities)
        assertEquals(32.50, result.second)
    }

    @Test
    fun `two voucher and one tshirt are 25,00`() {
        val mapQuantities = mutableMapOf<String, Int>("VOUCHER" to 2, "TSHIRT" to 1)
        val result = useCase.invoke(listOf(productVoucher, productTshirt, productMug), mapQuantities)

        assertEquals(25.00, result.second)
    }

    @Test
    fun `four tshirts, one voucher are 81,00`() {
        val mapQuantities = mutableMapOf<String, Int>("VOUCHER" to 1, "TSHIRT" to 4)
        val result = useCase.invoke(listOf(productVoucher, productTshirt, productMug), mapQuantities)

        assertEquals(81.00, result.second)
    }

    @Test
    fun `three tshirts, three voucher and one mug are 74,50`() {
        val mapQuantities = mutableMapOf<String, Int>("VOUCHER" to 3, "TSHIRT" to 3, "MUG" to 1)
        val result = useCase.invoke(listOf(productVoucher, productTshirt, productMug), mapQuantities)
        assertEquals(74.50, result.second)
    }
}
