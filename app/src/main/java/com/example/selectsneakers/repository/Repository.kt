package com.example.selectsneakers.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.selectsneakers.model.PaymentModel
import com.example.selectsneakers.remote.ApiService
import com.example.selectsneakers.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository {
    private val apiService: ApiService by lazy {
        RetrofitClient.create()
    }

    fun getPay(
         card_number: String,
         cvc_code: String,
         date: String,

    ): LiveData<PaymentModel> {

        val data = MutableLiveData<PaymentModel>()

        apiService.getPlayLists(card_number, cvc_code, date)
            .enqueue(object : Callback<PaymentModel> {
                override fun onResponse(
                    call: Call<PaymentModel>,
                    response: Response<PaymentModel>
                ) {
                    if (response.isSuccessful) {
                        data.value = response.body()
                    }
                }

                override fun onFailure(call: Call<PaymentModel>, t: Throwable) {
                    print(t.message)
                }
            })
        return data
    }
}