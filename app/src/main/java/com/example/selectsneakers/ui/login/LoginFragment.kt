package com.example.selectsneakers.ui.login

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.selectsneakers.R
import com.example.selectsneakers.core.ui.BaseFragment
import com.example.selectsneakers.databinding.FragmentLoginBinding
import com.example.selectsneakers.ui.home.HomeViewModel


class LoginFragment : BaseFragment<FragmentLoginBinding, HomeViewModel>() {
    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(inflater, container, false)
    }

    override fun initListeners() {
        super.initListeners()
        binding.tvSkip.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }
        binding.tvBack.setOnClickListener {
           findNavController().navigate( R.id.choiceFragment)
        }
    }

    override val viewModel: HomeViewModel
        get() = TODO("Not yet implemented")

}
