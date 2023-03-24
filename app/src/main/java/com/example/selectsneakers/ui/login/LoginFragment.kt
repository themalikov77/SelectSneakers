package com.example.selectsneakers.ui.login

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.selectsneakers.R
import com.example.selectsneakers.core.ui.BaseFragment
import com.example.selectsneakers.databinding.FragmentLoginBinding
import com.example.selectsneakers.ui.home.HomeViewModel


class LoginFragment : BaseFragment(R.layout.fragment_login) {
    private val binding by viewBinding(FragmentLoginBinding::bind)
    private val viewModel: HomeViewModel by viewModels()


    override fun initListeners() {
        super.initListeners()
        binding.tvSkip.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }
        binding.tvBack.setOnClickListener {
            findNavController().navigate(R.id.choiceFragment)
        }
    }
}


