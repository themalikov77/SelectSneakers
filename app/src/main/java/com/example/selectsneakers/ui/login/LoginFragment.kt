package com.example.selectsneakers.ui.login

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.selectsneakers.R
import com.example.selectsneakers.core.ui.BaseFragment
import com.example.selectsneakers.databinding.FragmentLoginBinding
import com.example.selectsneakers.ui.home.HomeViewModel
import com.google.firebase.auth.FirebaseAuth


class LoginFragment : BaseFragment(R.layout.fragment_login) {
    private val binding by viewBinding(FragmentLoginBinding::bind)
    private val viewModel: HomeViewModel by viewModels()
    private val mAuth = FirebaseAuth.getInstance()


    override fun initListeners() {
        super.initListeners()
        with(binding){
            tvSkip.setOnClickListener {
                findNavController().navigate(R.id.homeFragment)
            }
            tvBack.setOnClickListener {
                findNavController().navigate(R.id.choiceFragment)
            }
            btnEnter.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()
                if(email.isNotEmpty()&&password.isNotEmpty()){
                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
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
}


