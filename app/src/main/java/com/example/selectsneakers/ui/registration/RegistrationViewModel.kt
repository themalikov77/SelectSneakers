package com.example.selectsneakers.ui.registration

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.selectsneakers.core.ui.BaseViewModel
import com.example.selectsneakers.data.remote.Api
import com.example.selectsneakers.data.remote.RetrofitClient
import com.example.selectsneakers.data.remote.model.RegisterMap
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationViewModel : BaseViewModel() {

    private val api: Api = RetrofitClient.create()

    fun register(body: RegisterMap): LiveData<RegisterMap> {
        val liveData = MutableLiveData<RegisterMap>()
        api.register(body).enqueue(object : Callback<RegisterMap> {
            override fun onResponse(call: Call<RegisterMap>, response: Response<RegisterMap>) {
                if (response.isSuccessful) {
                    liveData.value = response.body()
                }
                Log.e("ololo", "onResponse: "+response.message())
            }

            override fun onFailure(call: Call<RegisterMap>, t: Throwable) {
                Log.e("ololo", "onFailure: " + t.message)
            }
        })
        return liveData
    }

}