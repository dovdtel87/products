package com.example.products.products.data.mapper

import com.example.products.products.data.model.Product
import com.example.products.products.data.network.model.ProductDto
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
}
