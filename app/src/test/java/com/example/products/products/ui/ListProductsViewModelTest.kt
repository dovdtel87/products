package com.example.products.products.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.products.data.model.Product
import com.example.products.products.domain.model.ProductUI
import com.example.products.products.domain.usecase.FetchProductsUseCase
import com.example.products.products.domain.usecase.UpdateProductsUIUseCase
import com.example.products.products.ui.list.ListProductsViewModel
import com.example.products.util.MainCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ListProductsViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var fetchProductsUseCase: FetchProductsUseCase
    @MockK
    private lateinit var updateProductsUIUseCase: UpdateProductsUIUseCase
    private lateinit var viewModel: ListProductsViewModel

    private val expectedProducts = listOf(
        Product("p1", "Product 1", 10.0),
        Product("p2", "Product 2", 15.0),
        Product("p3", "Product 3", 20.0)
    )
    private val expectedProductUIList = listOf(
        ProductUI("p1", "Product 1", 10.0, 0),
        ProductUI("p2", "Product 2", 15.0, 0),
        ProductUI("p3", "Product 3", 20.0, 0)
    )

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        coEvery { fetchProductsUseCase.invoke() } returns Result.success(expectedProducts)
        coEvery { updateProductsUIUseCase.invoke(any(), any()) } returns Pair(expectedProductUIList, 0.0)
        viewModel = ListProductsViewModel(fetchProductsUseCase, updateProductsUIUseCase)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `fetchProducts is called during initialization`()  = runTest {
        coVerify { fetchProductsUseCase.invoke() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `if fetchProducts success update UI is called`()  = runTest {
        coVerify { fetchProductsUseCase.invoke() }
        coVerify { updateProductsUIUseCase.invoke(any(), any()) }
    }

    @Test
    fun `onAddItem updates the UI state`() = runTest {
        coEvery { updateProductsUIUseCase.invoke(any(), any()) } returns Pair(expectedProductUIList, 0.0)

        val productCode = "p1"
        viewModel.onAddItem(productCode)
        coVerify { updateProductsUIUseCase.invoke(any(), any()) }
    }

    @Test
    fun `onRemove updates the UI state`() = runTest {
        coEvery { updateProductsUIUseCase.invoke(any(), any()) } returns Pair(expectedProductUIList, 0.0)

        val productCode = "p1"
        viewModel.onRemoveItem(productCode)
        coVerify { updateProductsUIUseCase.invoke(any(), any()) }
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }
}
