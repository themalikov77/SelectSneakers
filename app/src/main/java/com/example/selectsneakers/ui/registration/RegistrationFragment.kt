package com.example.selectsneakers.ui.registration

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.selectsneakers.R
import com.example.selectsneakers.core.ui.BaseFragment
import com.example.selectsneakers.data.remote.model.RegisterMap
import com.example.selectsneakers.databinding.FragmentRegistrationBinding
import com.example.selectsneakers.ui.home.HomeViewModel


class RegistrationFragment : BaseFragment(R.layout.fragment_registration) {

private val binding by viewBinding(FragmentRegistrationBinding::bind)
    private val viewModel: RegistrationViewModel by viewModels()

    override fun initListeners() {
        super.initListeners()
        binding.tvBack.setOnClickListener {
            findNavController().navigate(R.id.choiceFragment)
        }
    }
}