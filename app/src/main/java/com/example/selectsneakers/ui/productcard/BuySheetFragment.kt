package com.example.selectsneakers.ui.productcard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.selectsneakers.core.extension.initHorizontalAdapter
import com.example.selectsneakers.databinding.FragmentBuySheetBinding
import com.example.selectsneakers.ui.productcard.adapters.BuySizeAdapter
import com.example.selectsneakers.ui.productcard.adapters.ColorShoesAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BuySheetFragment : BottomSheetDialogFragment() {

    private val adapterSize = BuySizeAdapter()
    private val adapterColor = ColorShoesAdapter()
    private lateinit var binding: FragmentBuySheetBinding
    private val viewModel: ProductCardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBuySheetBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initObserver()

    }

    private fun initAdapter() {
        with(binding){
            idRecyclerSize.initHorizontalAdapter()
            idRecyclerSize.adapter = adapterSize
            recyclerColor.initHorizontalAdapter()
            recyclerColor.adapter = adapterColor
        }
    }

    private fun initObserver(){
        viewModel.getShoesColor().observe(viewLifecycleOwner) {
            adapterColor.addColorList(it)
        }
        viewModel.getSizeList().observe(viewLifecycleOwner){
            adapterSize.addSize(it)
        }
    }



}