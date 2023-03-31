package com.example.selectsneakers.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import com.example.selectsneakers.R
import com.example.selectsneakers.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navController = findNavController(R.id.nav_host_fragment)

        with(binding) {
            navController.addOnDestinationChangedListener { _, destination, _ ->
                navigationBar.isVisible =
                    destination.id == R.id.homeFragment || destination.id == R.id.favoriteFragment
            }
        }

        binding.navigationBar.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.homeFragment -> {
                    navController.navigate(R.id.homeFragment)
                    true
                }
                R.id.favoriteFragment -> {
                    navController.navigate(R.id.favoriteFragment)
                    true
                }
                else -> false
            }
        }
    }

}