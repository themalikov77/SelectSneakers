package com.example.selectsneakers.ui.registration

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.selectsneakers.R
import com.example.selectsneakers.core.ui.BaseFragment
import com.example.selectsneakers.data.remote.model.RegisterMap
import com.example.selectsneakers.databinding.FragmentRegistrationBinding
import com.example.selectsneakers.ui.home.HomeViewModel


class RegistrationFragment : BaseFragment<FragmentRegistrationBinding, RegistrationViewModel>() {

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegistrationBinding {
        return FragmentRegistrationBinding.inflate(inflater, container, false)
    }

    override val viewModel: RegistrationViewModel by lazy {
        ViewModelProvider(this)[RegistrationViewModel::class.java]
    }

    override fun initListeners() {
        super.initListeners()
        binding.tvBack.setOnClickListener {
            findNavController().navigate(R.id.choiceFragment)
        }

        binding.btnRegister.setOnClickListener {
            viewModel.register(
                RegisterMap(
                    username = binding.etNameAndFirstName.text.toString(),
                    email = binding.etLogin.text.toString(),
                    password = binding.etPassword.text.toString(),
                )
            ).observe(viewLifecycleOwner) {
                findNavController().navigate(R.id.homeFragment)
            }
        }
    }


}