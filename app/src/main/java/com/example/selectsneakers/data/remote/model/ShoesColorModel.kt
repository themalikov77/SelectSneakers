package com.example.selectsneakers.data.remote.model

import java.io.Serializable

data class ShoesColorModel(
    var shoesColorImg: String? = null,
    var isChoose: Boolean = false
) : Serializable
