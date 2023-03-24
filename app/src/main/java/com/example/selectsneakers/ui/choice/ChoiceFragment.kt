package com.example.selectsneakers.ui.choice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.selectsneakers.R
import com.example.selectsneakers.core.ui.BaseFragment
import com.example.selectsneakers.databinding.FragmentChoiceBinding
import com.example.selectsneakers.ui.home.HomeViewModel


class ChoiceFragment : BaseFragment<FragmentChoiceBinding, HomeViewModel>() {
    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentChoiceBinding {
        return FragmentChoiceBinding.inflate(inflater, container, false)
    }

    override val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this)[HomeViewModel::class.java]
    }

    override fun initListeners() {
        super.initListeners()
        binding.btnEnter.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }
        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.registrationFragment)
        }
        binding.tvSkip.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }
    }



}