package com.example.selectsneakers.repository

import android.util.Log
import com.example.selectsneakers.core.network.result.Resource
import com.example.selectsneakers.data.remote.RemoteDataSource
import com.example.selectsneakers.data.remote.model.ProductDetailList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class Repository(){

    private val dataSource: RemoteDataSource by lazy {
        RemoteDataSource()
    }
    fun getProductDetailList(id: Int): Flow<Resource<ProductDetailList>> = flow {
        emit(Resource.Loading())
        val response = dataSource.getProductDetailList(id)
        emit(response)
        response.data?.let { Log.e("ololo", it.description) }
    }.flowOn(Dispatchers.IO)


}