package com.example.products.data.mapper

import com.example.products.data.database.ProductEntity
import com.example.products.data.model.Product
import com.example.products.data.network.model.ProductDto
import javax.inject.Inject

class ProductMapper @Inject constructor() {

    fun map(productsDto: List<ProductDto>) : List<Product> {
        val productList = mutableListOf<Product>()
        productsDto.forEach{
            productList.add(
                Product(
                    code =  it.code,
                    name = it.name,
                    price = it.price
                )
            )
        }
        return productList
    }

    fun mapToProductEntity(productsDto: List<Product>) : List<ProductEntity> {
        val productListEntity = mutableListOf<ProductEntity>()
        productsDto.forEach{
            productListEntity.add(
                ProductEntity(
                    code =  it.code,
                    name = it.name,
                    price = it.price
                )
            )
        }
        return productListEntity
    }

    fun mapToProductFromEntity(productsDto: List<ProductEntity>) : List<Product> {
        val productList = mutableListOf<Product>()
        productsDto.forEach{
            productList.add(
                Product(
                    code =  it.code,
                    name = it.name,
                    price = it.price
                )
            )
        }
        return productList
    }
}
