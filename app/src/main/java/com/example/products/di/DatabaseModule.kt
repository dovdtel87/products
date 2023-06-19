package com.example.products.di

import android.content.Context
import androidx.room.Room
import com.example.products.data.database.ProductDatabase
import com.example.products.data.database.ProductsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): ProductDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            ProductDatabase::class.java,
            "Products.db"
        ).build()
    }

    @Provides
    fun provideProductDao(database: ProductDatabase): ProductsDao = database.productDao()
}
