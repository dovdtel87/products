package com.example.products

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.products.products.domain.usecase.FetchProductsUseCase
import com.example.products.products.domain.usecase.UpdateProductsUIUseCase
import com.example.products.products.ui.list.ListProductsViewModel
import com.example.products.products.ui.list.screen.ListProductsScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@MediumTest
@HiltAndroidTest
@OptIn(ExperimentalCoroutinesApi::class)
class ListProductsScreenTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createComposeRule()

    @Inject
    lateinit var fetchProductsUseCase: FetchProductsUseCase
    @Inject
    lateinit var updateProductsUIUseCase: UpdateProductsUIUseCase

    @Before
    fun init() {
        hiltRule.inject()
    }


    @Test
    fun testListProductsScreen_Success() = runTest {
        composeTestRule.setContent {
            ListProductsScreen(
                viewModel = ListProductsViewModel(fetchProductsUseCase, updateProductsUIUseCase),
            )
        }

        composeTestRule.onNodeWithText("Cabify Shop").assertExists()
        composeTestRule.onNodeWithText("Chair").assertExists()
        composeTestRule.onNodeWithText("Sofa").assertExists()
    }
}
