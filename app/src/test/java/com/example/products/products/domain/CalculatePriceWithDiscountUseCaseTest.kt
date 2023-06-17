package com.example.products.products.domain

import com.example.products.data.repository.DiscountsRepository
import com.example.products.data.model.Discount
import com.example.products.data.model.Product
import com.example.products.products.domain.usecase.CalculatePriceWithDiscountUseCase
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class CalculatePriceWithDiscountUseCaseTest {

    private val productVoucher = Product("VOUCHER","Voucher", 5.0)
    private val productTshirt = Product("TSHIRT","Tshirt", 20.0)
    private val productMug = Product("MUG","Mug", 7.50)

    @MockK
    lateinit var discountsRepository: DiscountsRepository

    private lateinit var useCase: CalculatePriceWithDiscountUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        useCase = CalculatePriceWithDiscountUseCase(discountsRepository)
    }

    @Test
    fun `one voucher cost 5,00 `() {
        every { discountsRepository.getDiscountForProduct("VOUCHER") } returns Discount.FreeItem(2, 1)
        val result = useCase.invoke(productVoucher, 1)
        assertEquals(5.00, result)
    }

    @Test
    fun `two voucher cost 5,00 `() {
        every { discountsRepository.getDiscountForProduct("VOUCHER") } returns Discount.FreeItem(2, 1)
        val result = useCase.invoke(productVoucher, 2)
        assertEquals(5.00, result)
    }

    @Test
    fun `three voucher cost 10,00 `() {
        every { discountsRepository.getDiscountForProduct("VOUCHER") } returns Discount.FreeItem(2, 1)
        val result = useCase.invoke(productVoucher, 3)
        assertEquals(10.00, result)
    }

    @Test
    fun `one tshirt cost 20,00 `() {
        every { discountsRepository.getDiscountForProduct("TSHIRT") } returns Discount.PriceReduction(3, 1.0)
        val result = useCase.invoke(productTshirt, 1)
        assertEquals(20.00, result)
    }

    @Test
    fun `two tshirt cost 40,00 `() {
        every { discountsRepository.getDiscountForProduct("TSHIRT") } returns Discount.PriceReduction(3, 1.0)
        val result = useCase.invoke(productTshirt, 2)
        assertEquals(40.00, result)
    }

    @Test
    fun `three tshirt cost 57,00 `() {
        every { discountsRepository.getDiscountForProduct("TSHIRT") } returns Discount.PriceReduction(3, 1.0)
        val result = useCase.invoke(productTshirt, 3)
        assertEquals(57.00, result)
    }

    @Test
    fun `one mug cost 7,50 `() {
        every { discountsRepository.getDiscountForProduct("MUG") } returns null
        val result = useCase.invoke(productMug, 1)
        assertEquals(7.50, result)
    }

    @Test
    fun `one mug cost 15,00 `() {
        every { discountsRepository.getDiscountForProduct("MUG") } returns null
        val result = useCase.invoke(productMug, 2)
        assertEquals(15.00, result)
    }

    @Test
    fun `one mug cost 22,50 `() {
        every { discountsRepository.getDiscountForProduct("MUG") } returns null
        val result = useCase.invoke(productMug, 3)
        assertEquals(22.50, result)
    }
}
