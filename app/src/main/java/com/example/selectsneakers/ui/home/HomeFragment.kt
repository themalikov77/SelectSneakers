package com.example.selectsneakers.ui.home

import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.selectsneakers.R
import com.example.selectsneakers.core.ui.BaseFragment
import com.example.selectsneakers.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel: HomeViewModel by viewModels()

}