package com.example.selectsneakers.ui.cart

import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.selectsneakers.R
import com.example.selectsneakers.core.ui.BaseFragment
import com.example.selectsneakers.data.remote.model.Favorite
import com.example.selectsneakers.databinding.FragmentCartBinding
import com.example.selectsneakers.ui.favorite.FavoriteAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CartFragment : BaseFragment(R.layout.fragment_cart) {

    private val binding by viewBinding(FragmentCartBinding::bind)
    private val db = FirebaseDatabase.getInstance().getReference("Users")
    private val adapterCart = FavoriteAdapter(this::onClick)

    companion object {
        const val KEY_FOR_CART_ID = "cart_ID"
        const val KEY_FOR_CART_IMG = "cart_IMG"
    }

    private fun onClick(id: Int, img: String) {
        val images = arrayListOf<String>()
        images.add(img)
        findNavController().navigate(
            R.id.productCartFragment, bundleOf(
                KEY_FOR_CART_ID to id,
                KEY_FOR_CART_IMG to images
            )
        )
    }

    private val mAuth = FirebaseAuth.getInstance()

    override fun initAdapters() {
        super.initAdapters()
        with(binding) {
            recyclerCart.layoutManager = LinearLayoutManager(requireContext())
            recyclerCart.adapter = adapterCart
        }
    }

    override fun initView() {
        super.initView()
        if (isAdded&&activity !=null){
            getData()
        }
    }

    private fun getData() {
        val listFavorite = arrayListOf<Favorite>()
        db.child(mAuth.uid.toString()).child("Cart")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (isAdded&&activity!=null){
                    listFavorite.clear()
                    if (snapshot.exists()) {
                        for (cart in snapshot.children) {
                            val favoriteData = cart.getValue(Favorite::class.java)
                            favoriteData?.let { listFavorite.add(it) }
                        }
                        adapterCart.addFavorite(listFavorite)
                        if (listFavorite.size < 1) {
                            binding.notCart.isVisible = true
                            binding.recyclerCart.isVisible = false
                        } else {
                            binding.notCart.isVisible = false
                            binding.recyclerCart.isVisible = true
                        }
                    }
                }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(requireContext(), error.toString(), Toast.LENGTH_SHORT).show()
                }
            })
    }

}