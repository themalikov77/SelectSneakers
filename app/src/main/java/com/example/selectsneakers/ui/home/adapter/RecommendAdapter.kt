package com.example.selectsneakers.ui.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.selectsneakers.databinding.ItemRecommendBinding

class RecommendAdapter : RecyclerView.Adapter<RecommendAdapter.RecommendViewHolder>() {

    private var listRecommend = arrayListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendViewHolder {
        return RecommendViewHolder(
            ItemRecommendBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = listRecommend.size

    override fun onBindViewHolder(holder: RecommendViewHolder, position: Int) {
        holder.bind(listRecommend[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addRecommend(recommend: List<String>){
        listRecommend.clear()
        listRecommend.addAll(recommend)
        notifyDataSetChanged()
    }

    inner class RecommendViewHolder(private val binding: ItemRecommendBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(recommend: String) {
            with(binding) {
                Glide.with(imgRecommend).load(recommend).into(imgRecommend)
            }
        }

    }

}