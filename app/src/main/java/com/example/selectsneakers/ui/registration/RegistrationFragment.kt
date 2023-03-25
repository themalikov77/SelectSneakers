package com.example.selectsneakers.ui.registration

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.selectsneakers.R
import com.example.selectsneakers.core.ui.BaseFragment
import com.example.selectsneakers.data.remote.model.RegisterMap
import com.example.selectsneakers.databinding.FragmentRegistrationBinding
import com.example.selectsneakers.ui.home.HomeViewModel
import com.google.firebase.auth.FirebaseAuth


class RegistrationFragment : BaseFragment(R.layout.fragment_registration) {

    private val binding by viewBinding(FragmentRegistrationBinding::bind)
    private val viewModel: RegistrationViewModel by viewModels()
    private val mAuth = FirebaseAuth.getInstance()

    override fun initListeners() {
        super.initListeners()
        with(binding){
            tvBack.setOnClickListener {
                findNavController().navigate(R.id.choiceFragment)
            }
            btnRegister.setOnClickListener {
                singUp()
            }
        }

    }

    private fun singUp() {
        with(binding){
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()){
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                    if (it.isSuccessful){
                        findNavController().navigate(R.id.homeFragment)
                    }else{
                        Toast.makeText(requireContext(), it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                Toast.makeText(requireContext(), "invalid", Toast.LENGTH_SHORT).show()
            }
        }
    }


}