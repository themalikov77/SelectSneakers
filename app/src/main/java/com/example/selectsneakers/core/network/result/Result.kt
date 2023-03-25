package com.example.selectsneakers.core.network.result


sealed class Resource<T>(
    val data :T? = null,
    val message : String? =null
){
    class Loading<T> : Resource<T>()
    class Success<T>(data: T?): Resource<T>(data = data)
    class Error<T>(message: String?, data: T? = null): Resource<T>(data, message)

}