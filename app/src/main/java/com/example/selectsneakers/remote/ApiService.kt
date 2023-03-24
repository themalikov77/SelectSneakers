package com.example.selectsneakers.remote
import com.example.selectsneakers.model.PaymentModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/payment/")
    fun getPlayLists(
        @Query("card_number") cardNumber: String,
        @Query("cvc_code") cvvCode: String,
        @Query("date") date: String,

    ): Call<PaymentModel>

}