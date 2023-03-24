package com.example.selectsneakers.ui.productcard.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.selectsneakers.databinding.ItemReviewBinding

class ReviewAdapter : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    private var listReview = arrayListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        return ReviewViewHolder(
            ItemReviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return listReview.size
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(listReview[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addReview(review: ArrayList<String>){
        listReview.clear()
        listReview.addAll(review)
        notifyDataSetChanged()
    }

    inner class ReviewViewHolder(private val binding: ItemReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(review:String) {
            binding.stars5.star1.height = 15
            if (review!=null){
                Glide.with(binding.reviewProfilImg).load(review).into(binding.reviewProfilImg)
            }else{
                Glide.with(binding.reviewProfilImg).load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQz9RJkNDp7C_87ZaWPnkr1ZbJPjdDodDUJWw&usqp=CAU").into(binding.reviewProfilImg)
            }

        }
    }
}