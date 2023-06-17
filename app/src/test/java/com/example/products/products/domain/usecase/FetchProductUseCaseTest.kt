package com.example.products.products.domain.usecase

import com.example.products.data.model.Product
import com.example.products.data.repository.ProductsRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class FetchProductUseCaseTest {

    @MockK
    private lateinit var productsRepository: ProductsRepository

    private lateinit var useCase: FetchProductsUseCase

    @Before
    fun setUp(){
        MockKAnnotations.init(this)
        useCase = FetchProductsUseCase(productsRepository)
    }


    @Test
    fun `invoke should return success result with fetched products`() = runBlocking {
        val expectedProducts = listOf(
            Product("p1", "Product 1", 10.0),
            Product("p2", "Product 2", 15.0),
            Product("p3", "Product 3", 20.0)
        )

        coEvery { productsRepository.fetchProducts() } returns Result.success(expectedProducts)
        val result = useCase.invoke()
        assertEquals(Result.success(expectedProducts), result)
    }

    @Test
    fun `invoke should return failure result when an exception is thrown`() = runBlocking {
        val expectedException = Exception("Failed to fetch products")
        coEvery { productsRepository.fetchProducts() } throws expectedException
        val fetchProductsUseCase = FetchProductsUseCase(productsRepository)
        val result = fetchProductsUseCase.invoke()

        assertEquals(Result.failure<List<Product>>(expectedException), result)
    }
}
