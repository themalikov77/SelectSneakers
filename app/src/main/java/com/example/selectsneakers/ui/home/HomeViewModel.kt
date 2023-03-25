package com.example.selectsneakers.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.selectsneakers.App
import com.example.selectsneakers.core.ui.BaseViewModel
import com.example.selectsneakers.data.remote.model.Products
import com.example.selectsneakers.utils.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel:BaseViewModel(){

    private val listRecommend = arrayListOf(
        "https://i.pinimg.com/236x/5c/96/69/5c96694ff1cd942ff6818b5808565bd4.jpg",
        "https://i.pinimg.com/236x/7c/bc/ee/7cbcee785c2419e0ccdfbe6ca81b3b08.jpg",
        "https://i.pinimg.com/236x/aa/cc/0c/aacc0cf645ae1c17528eeafaefc169ce.jpg",
        "https://i.pinimg.com/236x/50/30/a5/5030a56e96c1c2350647f260e6acebca.jpg",
        "https://i.pinimg.com/236x/40/38/c8/4038c87a8a470025eb08dac2775a3e60.jpg"
    )
    val recommendList = MutableLiveData<ArrayList<String>>()

    fun getShoesList(): LiveData<ArrayList<String>> {
        recommendList.value = listRecommend
        return recommendList
    }

    private val _getProductsState= MutableStateFlow<UIState<Products>>(UIState.Empty())
    val getProductsState = _getProductsState.asStateFlow()
    fun getProducts(){
        viewModelScope.launch(Dispatchers.IO) {
            App().repository.getProducts().collectFlow(_getProductsState)
        }
    }


}