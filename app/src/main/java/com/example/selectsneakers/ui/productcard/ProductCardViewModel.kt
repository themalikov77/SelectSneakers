package com.example.selectsneakers.ui.productcard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.selectsneakers.App
import com.example.selectsneakers.core.ui.BaseViewModel
import com.example.selectsneakers.data.remote.model.ProductDetailList
import com.example.selectsneakers.data.remote.model.ShoesColorModel
import com.example.selectsneakers.utils.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductCardViewModel() : BaseViewModel() {

    private val listShoes: ArrayList<String> = arrayListOf(
        "https://i.pinimg.com/236x/5c/96/69/5c96694ff1cd942ff6818b5808565bd4.jpg",
        "https://i.pinimg.com/236x/7c/bc/ee/7cbcee785c2419e0ccdfbe6ca81b3b08.jpg",
        "https://i.pinimg.com/236x/aa/cc/0c/aacc0cf645ae1c17528eeafaefc169ce.jpg",
        "https://i.pinimg.com/236x/50/30/a5/5030a56e96c1c2350647f260e6acebca.jpg",
        "https://i.pinimg.com/236x/40/38/c8/4038c87a8a470025eb08dac2775a3e60.jpg"
    )

    private val listReview: ArrayList<String> = arrayListOf(
        "https://i.pinimg.com/236x/5c/96/69/5c96694ff1cd942ff6818b5808565bd4.jpg",
        "https://i.pinimg.com/236x/7c/bc/ee/7cbcee785c2419e0ccdfbe6ca81b3b08.jpg",
        "https://i.pinimg.com/236x/aa/cc/0c/aacc0cf645ae1c17528eeafaefc169ce.jpg",
        "https://i.pinimg.com/236x/50/30/a5/5030a56e96c1c2350647f260e6acebca.jpg",
        "https://i.pinimg.com/236x/40/38/c8/4038c87a8a470025eb08dac2775a3e60.jpg"
    )
    private val listShoesSimilar: ArrayList<String> = arrayListOf(
        "https://i.pinimg.com/236x/5c/96/69/5c96694ff1cd942ff6818b5808565bd4.jpg",
        "https://i.pinimg.com/236x/7c/bc/ee/7cbcee785c2419e0ccdfbe6ca81b3b08.jpg",
        "https://i.pinimg.com/236x/aa/cc/0c/aacc0cf645ae1c17528eeafaefc169ce.jpg",
        "https://i.pinimg.com/236x/50/30/a5/5030a56e96c1c2350647f260e6acebca.jpg",
        "https://i.pinimg.com/236x/40/38/c8/4038c87a8a470025eb08dac2775a3e60.jpg"
    )
    private val listShoesColor: ArrayList<ShoesColorModel> = arrayListOf(
        ShoesColorModel(
            "https://i.pinimg.com/236x/5c/96/69/5c96694ff1cd942ff6818b5808565bd4.jpg",
            false
        ),
        ShoesColorModel(
            "https://i.pinimg.com/236x/7c/bc/ee/7cbcee785c2419e0ccdfbe6ca81b3b08.jpg",
            false
        ),
        ShoesColorModel(
            "https://i.pinimg.com/236x/5c/96/69/5c96694ff1cd942ff6818b5808565bd4.jpg",
            false
        ),
        ShoesColorModel(
            "https://i.pinimg.com/236x/aa/cc/0c/aacc0cf645ae1c17528eeafaefc169ce.jpg",
            false
        ),
        ShoesColorModel(
            "https://i.pinimg.com/236x/50/30/a5/5030a56e96c1c2350647f260e6acebca.jpg",
            false,
        ),
    )


    val shoesList = MutableLiveData<ArrayList<String>>()
    val shoesColorList = MutableLiveData<ArrayList<ShoesColorModel>>()
    val shoesSimilarList = MutableLiveData<ArrayList<String>>()
    val reviewList = MutableLiveData<ArrayList<String>>()


    fun getShoesList(): LiveData<ArrayList<String>> {
        shoesList.value = listShoes
        return shoesList
    }

    fun getShoesColor(): LiveData<ArrayList<ShoesColorModel>> {
        shoesColorList.value = listShoesColor
        return shoesColorList
    }

    fun getShoesSimilar(): LiveData<ArrayList<String>> {
        shoesSimilarList.value = listShoesSimilar
        return shoesSimilarList
    }

    fun getReview(): LiveData<ArrayList<String>> {
        reviewList.value = listReview
        return reviewList
    }

    private val _getProductDetailState = MutableStateFlow<UIState<ProductDetailList>>(UIState.Empty())
    val getProductDetailState = _getProductDetailState.asStateFlow()


    fun getProductDetailList(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
           App().repository.getProductDetailList(id).collectFlow(_getProductDetailState)
        }
    }


}