package com.example.selectsneakers.data.remote

import com.example.selectsneakers.core.network.BaseDataSource
import com.example.selectsneakers.core.network.RetrofitClient
import com.example.selectsneakers.core.network.result.Resource
import com.example.selectsneakers.data.remote.model.ProductDetailList
import com.example.selectsneakers.data.remote.model.Products

class RemoteDataSource() : BaseDataSource() {

    private val apiService: Api by lazy {
        RetrofitClient.create()
    }

    suspend fun getProductDetailList(id: Int): Resource<ProductDetailList> {
        return getResult {
            apiService.getProductDetailList(id)
        }
    }

    suspend fun getProducts():Resource<Products>{
        return getResult {
            apiService.getProducts()
        }
    }

}