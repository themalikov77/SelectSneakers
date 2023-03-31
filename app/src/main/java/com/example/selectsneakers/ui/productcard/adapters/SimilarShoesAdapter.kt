package com.example.selectsneakers.ui.productcard.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.selectsneakers.data.remote.model.Product
import com.example.selectsneakers.databinding.ItemSimilarBinding

class SimilarShoesAdapter(val onClick: (id: Int) -> Unit) :
    RecyclerView.Adapter<SimilarShoesAdapter.SimilarShoesViewHolder>() {

    private var listSimilar = arrayListOf<Product>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarShoesViewHolder {
        return SimilarShoesViewHolder(
            ItemSimilarBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: SimilarShoesViewHolder, position: Int) {
        holder.bind(listSimilar[position])
    }

//    @SuppressLint("NotifyDataSetChanged")
//    fun addSimilarShoes(list: List<Product>) {
//        listSimilar.clear()
//        listSimilar.addAll(list)
//        notifyDataSetChanged()
//    }

    fun addSimilarPage(list: List<Product>) {
        listSimilar.addAll(list)
        notifyItemChanged(itemCount - 1)
    }

    override fun getItemCount(): Int {
        Log.e("ololo", "size: ${listSimilar.size}")
        return listSimilar.size
    }


    inner class SimilarShoesViewHolder(private val binding: ItemSimilarBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(imgSimilar: Product) {
            with(binding) {
                itemView.setOnClickListener {
                    onClick(imgSimilar.id)
                }

                Glide.with(binding.imgSimilar).load(imgSimilar.images[0].image)
                    .into(binding.imgSimilar)
                textName.text = imgSimilar.name
                textDescription.text = imgSimilar.description
                textPrice.text = imgSimilar.price
            }
        }
    }
}