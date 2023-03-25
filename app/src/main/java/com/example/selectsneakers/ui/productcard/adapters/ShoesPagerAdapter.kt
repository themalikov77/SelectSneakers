package com.example.selectsneakers.ui.productcard.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.selectsneakers.databinding.ItemShoesPagerBinding

class ShoesPagerAdapter : RecyclerView.Adapter<ShoesPagerAdapter.ShoesPagerViewHolder>() {

    private val listShoes = arrayListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoesPagerViewHolder {
        return ShoesPagerViewHolder(
            ItemShoesPagerBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount()=listShoes.size


    override fun onBindViewHolder(holder: ShoesPagerViewHolder, position: Int) {
        holder.bind(listShoes[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addShoes(list: List<String>) {
        listShoes.addAll(list)
        notifyDataSetChanged()
    }

    inner class ShoesPagerViewHolder(private val binding: ItemShoesPagerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(imgShoes: String) {
            Glide.with(binding.imgPagerShoes).load(imgShoes).into(binding.imgPagerShoes)
        }
    }
}