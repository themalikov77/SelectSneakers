package com.example.selectsneakers.ui.favorite

import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
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
    private val adapterFavorite = FavoriteAdapter()
    private val mAuth = FirebaseAuth.getInstance()

    override fun initAdapters() {
        with(binding) {
            recyclerFavorite.layoutManager = LinearLayoutManager(requireContext())
            recyclerFavorite.adapter = adapterFavorite
        }
    }

    override fun initView() {
        super.initView()
        with(binding) {
          getData()
        }
    }



    private fun getData(){
        val listFavorite = arrayListOf<Favorite>()
        db.child(mAuth.uid.toString()).child("Favorite")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
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
                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(requireContext(), error.toString(), Toast.LENGTH_SHORT).show()
                }

            })
    }


    override fun initObserver() {
        super.initObserver()
//        viewModel.getFavorite().observe(viewLifecycleOwner) {
//        //    adapterFavorite.addFavorite(it)
//        }

    }

}