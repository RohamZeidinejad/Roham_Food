package com.shahpourkhast.rohamfood.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.shahpourkhast.rohamfood.R
import com.shahpourkhast.rohamfood.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import nl.joery.animatedbottombar.AnimatedBottomBar

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        //---------------------------------------------------------

        setupBottomNav()
    }

    //---------------------------------------------------------

    private fun setupBottomNav() {

        binding.bottomNav.setOnTabSelectListener(object : AnimatedBottomBar.OnTabSelectListener {
            override fun onTabSelected(lastIndex: Int, lastTab: AnimatedBottomBar.Tab?, newIndex: Int, newTab: AnimatedBottomBar.Tab) {

                navController.navigate(newTab.id)

            }
        })

        navController.addOnDestinationChangedListener { _, destination, _ ->

            val selectedTab = binding.bottomNav.tabs.find { it.id == destination.id }
            selectedTab?.let {

                binding.bottomNav.selectTab(it, false)

            }
        }
    }
}