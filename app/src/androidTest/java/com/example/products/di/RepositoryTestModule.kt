package com.example.products.di

import com.example.products.data.repository.DiscountsRepository
import com.example.products.data.repository.ProductsRepository
import com.example.products.products.domain.repository.FakeDiscountRepositoryImpl
import com.example.products.repository.FakeProductsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class]
)
object RepositoryTestModule {

    @Singleton
    @Provides
    fun provideProductsRepository(): ProductsRepository {
        return FakeProductsRepository()
    }

    @Singleton
    @Provides
    fun provideDiscountRepository(): DiscountsRepository {
        return FakeDiscountRepositoryImpl()
    }
}
