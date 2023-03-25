package com.example.selectsneakers.ui.productcard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.selectsneakers.databinding.FragmentBuySheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BuySheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentBuySheetBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBuySheetBinding.inflate(inflater,container,false)
        return binding.root
    }


}