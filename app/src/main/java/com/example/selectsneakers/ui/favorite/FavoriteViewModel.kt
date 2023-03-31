package com.example.selectsneakers.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.selectsneakers.core.ui.BaseViewModel
import com.example.selectsneakers.data.remote.model.Favorite

class FavoriteViewModel : BaseViewModel() {

    private val listFavorite = arrayListOf(
        Favorite(
            img = "https://i.pinimg.com/236x/5c/96/69/5c96694ff1cd942ff6818b5808565bd4.jpg",
            description = "Mens shoes",
            size = "41",
            price = "4590c",
            name = "Nike sneakers",
            color = "good"
        ),
        Favorite(
            img = "https://i.pinimg.com/236x/aa/cc/0c/aacc0cf645ae1c17528eeafaefc169ce.jpg",
            description = "Mens shoes",
            size = "41",
            price = "4590c",
            name = "Nike sneakers",
            color = "good"
        ),
        Favorite(
            img = "https://i.pinimg.com/236x/50/30/a5/5030a56e96c1c2350647f260e6acebca.jpg",
            description = "Mens shoes",
            size = "41",
            price = "4590c",
            name = "Nike sneakers",
            color = "good"
        ),
        Favorite(
            img = "https://i.pinimg.com/236x/40/38/c8/4038c87a8a470025eb08dac2775a3e60.jpg",
            description = "Mens shoes",
            size = "41",
            price = "4590c",
            name = "Nike sneakers",
            color = "good"
        ),
        Favorite(
            img = "https://i.pinimg.com/236x/5c/96/69/5c96694ff1cd942ff6818b5808565bd4.jpg",
            description = "Mens shoes",
            size = "41",
            price = "4590c",
            name = "Nike sneakers",
            color = "good"
        ),
        Favorite(
            img = "https://i.pinimg.com/236x/5c/96/69/5c96694ff1cd942ff6818b5808565bd4.jpg",
            description = "Mens shoes",
            size = "41",
            price = "4590c",
            name = "Nike sneakers",
            color = "good"
        ),
    )
    val favoriteList = MutableLiveData<ArrayList<Favorite>>()

    fun getFavorite(): LiveData<ArrayList<Favorite>> {
        favoriteList.value = listFavorite
        return favoriteList
    }

}