package com.example.selectsneakers.ui.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.selectsneakers.databinding.ItemBrandsBinding

class BrandsAdapter : RecyclerView.Adapter<BrandsAdapter.BrandsViewHolder>() {

    private var listBrands = arrayListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandsViewHolder {
        return BrandsViewHolder(
            ItemBrandsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount()=listBrands.size


    override fun onBindViewHolder(holder: BrandsViewHolder, position: Int) {
        holder.bind(listBrands[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addBrands(brands: List<String>){
        listBrands.clear()
        listBrands.addAll(brands)
        notifyDataSetChanged()

    }

    inner class BrandsViewHolder(private val binding: ItemBrandsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(brand: String) {
            with(binding) {
                tvBrands.text = brand
            }
        }
    }

}