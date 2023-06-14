package com.example.products.di

import com.example.products.products.data.ProductsRepository
import com.example.products.products.data.ProductsRepositoryImpl
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
    abstract fun bindUserRepository(repository: ProductsRepositoryImpl): ProductsRepository
}
