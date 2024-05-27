package com.github.emmpann.cinemaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.github.emmpann.cinemaapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = (supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment).navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.playFragment -> {
                    binding.bottomNav.visibility = View.VISIBLE
                }
                R.id.upcomingFragment -> {
                    binding.bottomNav.visibility = View.VISIBLE
                }
                R.id.searchFragment -> {
                    binding.bottomNav.visibility = View.VISIBLE
                }
                R.id.favoriteFragment -> {
                    binding.bottomNav.visibility = View.VISIBLE
                }
                else -> {
                    binding.bottomNav.visibility = View.GONE
                }
            }
        }
        setupWithNavController(binding.bottomNav, navController)
    }
}