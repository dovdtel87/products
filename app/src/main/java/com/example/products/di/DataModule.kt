package com.example.products.di

import com.example.products.products.data.DiscountsRepository
import com.example.products.products.data.ProductsRepository
import com.example.products.products.data.ProductsRepositoryImpl
import com.example.products.products.data.network.model.FakeDiscountRepositoryImpl
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
