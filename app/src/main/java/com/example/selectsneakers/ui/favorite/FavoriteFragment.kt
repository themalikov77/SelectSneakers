package com.example.selectsneakers.ui.favorite

import android.util.Log
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.selectsneakers.R
import com.example.selectsneakers.core.ui.BaseFragment
import com.example.selectsneakers.data.remote.model.Favorite
import com.example.selectsneakers.databinding.FragmentFavoriteBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FavoriteFragment : BaseFragment(R.layout.fragment_favorite) {

    private val binding by viewBinding(FragmentFavoriteBinding::bind)
    private val viewModel: FavoriteViewModel by viewModels()
    private val db = FirebaseDatabase.getInstance().getReference("Users")
    private val adapterFavorite = FavoriteAdapter(this::onClick)


    companion object {
        const val KEY_FOR_FAVORITE_ID = "FAVORITE_ID"
        const val KEY_FOR_FAVORITE_IMG = "FAVORITE_IMG"
    }


    private fun onClick(id: Int, img: String) {
        val images = arrayListOf<String>()
        images.add(img)
        findNavController().navigate(
            R.id.productCartFragment, bundleOf(
                KEY_FOR_FAVORITE_ID to id,
                KEY_FOR_FAVORITE_IMG to images
            )
        )
    }

    private val mAuth = FirebaseAuth.getInstance()

    override fun initAdapters() {
        with(binding) {
            recyclerFavorite.layoutManager = LinearLayoutManager(requireContext())
            recyclerFavorite.adapter = adapterFavorite
        }
    }

    override fun initView() {
        super.initView()
        if (isAdded&&activity !=null){
            getData()
        }
    }

    private fun getData(){
        val listFavorite = arrayListOf<Favorite>()
        db.child(mAuth.uid.toString()).child("Favorite")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (isAdded&&activity!=null){
                        listFavorite.clear()
                        if (snapshot.exists()){
                            for (favorite in snapshot.children){
                                val favoriteData = favorite.getValue(Favorite::class.java)
                                favoriteData?.let {listFavorite.add(it)}
                            }
                            adapterFavorite.addFavorite(listFavorite)
                            if (listFavorite.size <1){
                                binding.notFavorite.isVisible = true
                                binding.recyclerFavorite.isVisible = false
                            }else{
                                binding.notFavorite.isVisible = false
                                binding.recyclerFavorite.isVisible = true
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