package com.example.selectsneakers.ui.productcard.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.selectsneakers.databinding.ItemBuySizeBinding

class BuySizeAdapter : RecyclerView.Adapter<BuySizeAdapter.BuySizeViewHolder>() {

    private var listSize = arrayListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuySizeViewHolder {
        return BuySizeViewHolder(
            ItemBuySizeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = listSize.size

    override fun onBindViewHolder(holder: BuySizeViewHolder, position: Int) {
        holder.bind(listSize[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addSize(size: List<String>){
        listSize.clear()
        listSize.addAll(size)
        notifyDataSetChanged()
    }


    inner class BuySizeViewHolder(private val binding: ItemBuySizeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(size: String) {
            with(binding) {
                Glide.with(imgSize).load(size).into(imgSize)
            }
        }

    }

}