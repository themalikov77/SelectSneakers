package com.example.selectsneakers.ui.favorite

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.selectsneakers.data.remote.model.Favorite
import com.example.selectsneakers.databinding.ItemFavoriteBinding

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewModel>() {

    private var listFavorite = arrayListOf<Favorite>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewModel {
        return FavoriteViewModel(
            ItemFavoriteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = listFavorite.size

    override fun onBindViewHolder(holder: FavoriteViewModel, position: Int) {
        holder.bind(listFavorite[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addFavorite(favorite: List<Favorite>){
        listFavorite.clear()
        listFavorite.addAll(favorite)
        notifyDataSetChanged()
    }


    inner class FavoriteViewModel(private val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: Favorite) {
            with(binding) {
                Glide.with(imgFavorite).load(favorite.img).into(imgFavorite)
                tvFavoriteDesc.text = favorite.description
                imgFavoriteSize.text = favorite.size
                tvFavoritePrice.text =favorite.price
                tvFavoriteName.text = favorite.name

            }
        }

    }
}