package com.example.selectsneakers.core.network

import com.example.selectsneakers.BuildConfig
import com.example.selectsneakers.data.remote.Api
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {
    companion object {
        fun create(): Api {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)//отвечает за данные с бэк

            // для проверки соединения телефона с бэк
            val okHttpClient = OkHttpClient.Builder().writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .build()
            val retrofit = Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build()
            return retrofit.create(Api::class.java)
        }

    }
}