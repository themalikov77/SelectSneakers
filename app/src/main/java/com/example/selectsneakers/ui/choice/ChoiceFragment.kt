package com.example.selectsneakers.ui.choice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.selectsneakers.R
import com.example.selectsneakers.core.ui.BaseFragment
import com.example.selectsneakers.databinding.FragmentChoiceBinding
import com.example.selectsneakers.ui.home.HomeViewModel


class ChoiceFragment : BaseFragment(R.layout.fragment_choice) {
   private val binding by viewBinding(FragmentChoiceBinding::bind)

    private val viewModel: HomeViewModel by viewModels()

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