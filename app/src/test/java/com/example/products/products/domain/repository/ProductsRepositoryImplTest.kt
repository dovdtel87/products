package com.example.products.products.domain.repository

import com.example.products.data.database.ProductEntity
import com.example.products.data.database.ProductsDao
import com.example.products.data.mapper.ProductMapper
import com.example.products.data.network.api.ProductsApi
import com.example.products.data.network.model.ProductDto
import com.example.products.data.network.model.ProductResponseDto
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class ProductsRepositoryImplTest {

    @MockK
    private lateinit var  productsApi: ProductsApi
    @MockK
    private lateinit var productsDao: ProductsDao

    private lateinit var productsRepositoryImpl: ProductsRepositoryImpl

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        productsRepositoryImpl = ProductsRepositoryImpl(productsApi, ProductMapper(), productsDao)
    }

    @Test
    fun `when fetchProducts is called and there is a list in the cache it returns that list`() = runBlocking {
        val productsEntityList = listOf(ProductEntity("VOUCHER", "Voucher", 5.0),ProductEntity("TSHIRT", "Tshirt", 20.0), ProductEntity("MUG", "Mug", 7.50) )
        coEvery { productsDao.getProducts() } returns productsEntityList

        val response = productsRepositoryImpl.fetchProducts()

        coVerify (exactly = 0) { productsApi.fetchProducts() }

        val data = response .getOrDefault(null) // .data?.get(0)
        TestCase.assertEquals("VOUCHER", data?.get(0)?.code)
    }

    @Test
    fun `when fetchProducts is called and there is no list in the cache it calls the api to fetch it`() = runBlocking {
        val productsList = listOf(ProductDto("VOUCHER", "Voucher", 5.0),ProductDto("TSHIRT", "Tshirt", 20.0), ProductDto("MUG", "Mug", 7.50) )

        coEvery { productsDao.getProducts() } returns emptyList()
        coEvery { productsApi.fetchProducts()} returns ProductResponseDto(productsList)
        coEvery { productsDao.insertProducts(any())} returns Unit

        val response = productsRepositoryImpl.fetchProducts()

        coVerify (exactly = 1) { productsApi.fetchProducts() }

        val data = response .getOrDefault(null)
        TestCase.assertEquals("VOUCHER", data?.get(0)?.code)
    }
}
