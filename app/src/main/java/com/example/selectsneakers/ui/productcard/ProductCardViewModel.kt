package com.example.selectsneakers.ui.productcard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.selectsneakers.App
import com.example.selectsneakers.core.ui.BaseViewModel
import com.example.selectsneakers.data.remote.model.ProductDetailList
import com.example.selectsneakers.data.remote.model.Products
import com.example.selectsneakers.data.remote.model.ProductsImageSerializers
import com.example.selectsneakers.data.remote.model.ShoesColorModel
import com.example.selectsneakers.utils.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductCardViewModel : BaseViewModel() {

    private val listReview: ArrayList<String> = arrayListOf(
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





    private val shoesColorList = MutableLiveData<ArrayList<ShoesColorModel>>()
    private val reviewList = MutableLiveData<ArrayList<String>>()
    private val arraySize = MutableLiveData<ArrayList<String>>()



    private val sizeArray = arrayListOf(
        "45", "44","88","88", "88", "88", "88", "88", "88"
    )


    fun getSizeList():LiveData<ArrayList<String>>{
        arraySize.value = sizeArray
        return arraySize
    }





    fun getShoesColor(): LiveData<ArrayList<ShoesColorModel>> {
        shoesColorList.value = listShoesColor
        return shoesColorList
    }



    fun getReview(): LiveData<ArrayList<String>> {
        reviewList.value = listReview
        return reviewList
    }

    private val _getProductDetailState =
        MutableStateFlow<UIState<ProductDetailList>>(UIState.Empty())
    val getProductDetailState = _getProductDetailState.asStateFlow()


    fun getProductDetailList(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            App().repository.getProductDetailList(id).collectFlow(_getProductDetailState)
        }
    }

    private val _getImagesByIdState =
        MutableStateFlow<UIState<ProductsImageSerializers>>(UIState.Empty())
    val getImagesByIdState = _getImagesByIdState.asStateFlow()

    fun getImagesById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            App().repository.getImagesById(id).collectFlow(_getImagesByIdState)
        }
    }

    private val _getProductsState = MutableStateFlow<UIState<Products>>(UIState.Empty())
    val getProductsState = _getProductsState.asStateFlow()
    fun getProducts(page:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            App().repository.getProducts(page).collectFlow(_getProductsState)
        }
    }


}