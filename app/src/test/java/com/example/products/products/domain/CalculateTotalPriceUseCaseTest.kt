package com.example.products.products.domain

import com.example.products.products.data.DiscountsRepository
import com.example.products.products.data.model.Discount
import com.example.products.products.data.model.Product
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class CalculateTotalPriceUseCaseTest {

    private val productVoucher = Product("VOUCHER","Voucher", 5.0)
    private val productTshirt = Product("TSHIRT","Tshirt", 20.0)
    private val productMug = Product("MUG","Mug", 7.50)

    @MockK
    lateinit var discountsRepository: DiscountsRepository

    lateinit var useCase: CalculateTotalPriceUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        useCase = CalculateTotalPriceUseCase(discountsRepository)
    }

    @Test
    fun `one voucher, one tshirt and one mug are 32,50`() {
        every { discountsRepository.getDiscountForProduct("MUG") } returns null
        every { discountsRepository.getDiscountForProduct("VOUCHER") } returns Discount.FreeItem(2, 1)
        every { discountsRepository.getDiscountForProduct("TSHIRT") } returns Discount.PriceReduction(3, 1.0)
        val result = useCase.invoke(listOf(productVoucher.apply { quantity = 1 }, productTshirt.apply { quantity = 1 }, productMug.apply { quantity = 1 }))
        assertEquals(32.50, result)
    }

    @Test
    fun `two voucher and one tshirt are 25,00`() {
        every { discountsRepository.getDiscountForProduct("VOUCHER") } returns Discount.FreeItem(2, 1)
        every { discountsRepository.getDiscountForProduct("TSHIRT") } returns Discount.PriceReduction(3, 1.0)
        val result = useCase.invoke(listOf(productVoucher.apply { quantity = 2 }, productTshirt.apply { quantity = 1 }))
        assertEquals(25.00, result)
    }

    @Test
    fun `four tshirts, one voucher are 81,00`() {
        every { discountsRepository.getDiscountForProduct("VOUCHER") } returns Discount.FreeItem(2, 1)
        every { discountsRepository.getDiscountForProduct("TSHIRT") } returns Discount.PriceReduction(3, 1.0)
        val result = useCase.invoke(listOf(productVoucher.apply { quantity = 1 }, productTshirt.apply { quantity = 4 }))
        assertEquals(81.00, result)
    }

    @Test
    fun `three tshirts, three voucher and one mug are 74,50`() {
        every { discountsRepository.getDiscountForProduct("MUG") } returns null
        every { discountsRepository.getDiscountForProduct("VOUCHER") } returns Discount.FreeItem(2, 1)
        every { discountsRepository.getDiscountForProduct("TSHIRT") } returns Discount.PriceReduction(3, 1.0)
        val result = useCase.invoke(listOf(productVoucher.apply { quantity = 3 }, productTshirt.apply { quantity = 3 },  productMug.apply { quantity = 1 }))
        assertEquals(74.50, result)
    }
}
