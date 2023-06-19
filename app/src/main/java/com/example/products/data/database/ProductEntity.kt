package com.example.products.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey val code: String,
    val name: String,
    val price: Double
)
