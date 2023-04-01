package com.example.selectsneakers.ui.launch

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.selectsneakers.R
import com.example.selectsneakers.core.ui.BaseFragment
import com.example.selectsneakers.databinding.FragmentLaunchBinding
import com.example.selectsneakers.ui.home.HomeViewModel
import com.google.firebase.auth.FirebaseAuth


class LaunchFragment : BaseFragment(R.layout.fragment_launch) {
    private var shortAnimationDuration: Int = 0
    private val binding by viewBinding(FragmentLaunchBinding::bind)
    private val mAuth = FirebaseAuth.getInstance()
    private val viewModel: HomeViewModel by viewModels()



    override fun initListeners() {
        super.initListeners()
        shortAnimationDuration = resources.getInteger(android.R.integer.config_shortAnimTime)
        Handler(Looper.getMainLooper()).postDelayed({
            binding.imgSelect.apply {
                alpha = 0f
                visibility = View.VISIBLE
                animate().alpha(1f).duration = shortAnimationDuration.toLong()
            }
        }, 1000)


        Handler(Looper.getMainLooper()).postDelayed({
            binding.imgSneakers.apply {
                alpha = 0f
                visibility = View.VISIBLE
                animate().alpha(1f).duration = shortAnimationDuration.toLong()
            }
        }, 2000)
        Handler(Looper.getMainLooper()).postDelayed({
            binding.tvWelcome.apply {
                alpha = 0f
                visibility = View.VISIBLE
                animate().alpha(1f).duration = shortAnimationDuration.toLong()

            }
        }, 3000)
        Handler(Looper.getMainLooper()).postDelayed({
            binding.linear.apply {
                alpha = 0f
                visibility = View.VISIBLE
                animate().alpha(1f).duration = shortAnimationDuration.toLong()
            }
        }, 4000)
        Handler(Looper.getMainLooper()).postDelayed({
            binding.later.apply {
                alpha = 0f
                visibility = View.VISIBLE
                animate().alpha(1f).duration = shortAnimationDuration.toLong()
            }
        }, 4000)
        Handler(Looper.getMainLooper()).postDelayed({
            binding.skip.apply {
                alpha = 0f
                visibility = View.VISIBLE
                animate().alpha(1f).duration = shortAnimationDuration.toLong()
            }
            if (mAuth.currentUser != null) {
                findNavController().navigate(R.id.homeFragment)
            }
        }, 4000)
        binding.skip.setOnClickListener {
            findNavController().navigate(R.id.choiceFragment)
        }
    }

}