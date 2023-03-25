package com.example.selectsneakers.data.remote

import com.example.selectsneakers.data.remote.model.ProductDetailList
import com.example.selectsneakers.data.remote.model.Products
import com.example.selectsneakers.data.remote.model.RegisterMap
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Api {
    @GET("products/{id}/")
    suspend fun getProductDetailList(
        @Path("id") id: Int
    ): Response<ProductDetailList>

    @GET("products/")
    suspend fun getProducts():Response<Products>



}