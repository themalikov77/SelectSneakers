package com.example.selectsneakers.data.remote.model


data class Products(
    val results: List<Product>
)

data class Product(
    val id:Int,
    val images: List<ProductsImageSerializers>,
    val name: String,
    val description: String,
    val price: String,
    val color:String,
    val size: Int,
    val season: Int
)

data class ProductsImageSerializers(
    val id: Int,
    val image: String,
    val products: Int
)
