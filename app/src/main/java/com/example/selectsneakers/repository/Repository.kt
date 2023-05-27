package com.example.selectsneakers.repository

import com.example.selectsneakers.core.network.result.Resource
import com.example.selectsneakers.data.remote.RemoteDataSource
import com.example.selectsneakers.data.remote.model.ProductDetailList
import com.example.selectsneakers.data.remote.model.Products
import com.example.selectsneakers.data.remote.model.ProductsImageSerializers
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
    }.flowOn(Dispatchers.IO)

    fun getProducts(page:Int):Flow<Resource<Products>> = flow{
        emit(Resource.Loading())
        val response = dataSource.getProducts(page)
        emit(response)
    }.flowOn(Dispatchers.IO)

    fun getImagesById(id: Int): Flow<Resource<ProductsImageSerializers>> = flow{
        emit(Resource.Loading())
        val response = dataSource.getImagesById(id)
        emit(response)
    }.flowOn(Dispatchers.IO)


}