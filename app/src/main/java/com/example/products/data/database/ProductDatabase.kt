package com.example.products.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ProductEntity::class], version = 1)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun productDao(): ProductsDao

    companion object {
        private const val DATABASE_NAME = "product_database"

        fun build(context: Context): ProductDatabase {
            return Room.databaseBuilder(context, ProductDatabase::class.java, DATABASE_NAME)
                .build()
        }
    }
}
