package com.example.products.di

import com.example.products.data.repository.DiscountsRepository
import com.example.products.data.repository.ProductsRepository
import com.example.products.products.domain.repository.ProductsRepositoryImpl
import com.example.products.products.domain.repository.FakeDiscountRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindProductsRepository(repository: ProductsRepositoryImpl): ProductsRepository

    @Singleton
    @Binds
    abstract fun bindDiscountsRepository(repository: FakeDiscountRepositoryImpl): DiscountsRepository
}
