package com.example.selectsneakers.ui.allreviews.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.selectsneakers.databinding.ItemAllReviewsBinding


class AllReviewAdapter : RecyclerView.Adapter<AllReviewAdapter.AllReviewViewHolder>() {

    private var listAllReviews = arrayListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllReviewViewHolder {
        return AllReviewViewHolder(
            ItemAllReviewsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount()=listAllReviews.size

    override fun onBindViewHolder(holder: AllReviewViewHolder, position: Int) {
        holder.bind(listAllReviews[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addAllReviews(reviews: ArrayList<String>){
        listAllReviews.clear()
        listAllReviews.addAll(reviews)
        notifyDataSetChanged()
    }

    inner class AllReviewViewHolder(private val binding: ItemAllReviewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(reviews: String) {
            Glide.with(binding.reviewProfilImg).load(reviews).into(binding.reviewProfilImg)
        }
    }
}