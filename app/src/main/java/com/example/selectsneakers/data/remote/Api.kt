package com.example.selectsneakers.data.remote

import com.example.selectsneakers.data.remote.model.RegisterMap
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {

    @POST("register/")
    fun register(@Body body: RegisterMap): Call<RegisterMap>

}