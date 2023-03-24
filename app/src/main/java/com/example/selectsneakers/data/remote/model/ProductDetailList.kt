package com.example.selectsneakers.data.remote.model


data class ProductDetailList (

    val id: Int,
    val name: String,
    val description: String,
    val price: String,
    val color: String,
    val size: Int,
    val season: Int,
    val images: List<Int>
        )

data class Image(
    val id: Int? = null
)