package com.goms.presentation.view.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.presentation.R
import com.example.presentation.databinding.ActivityMainBinding
import com.goms.presentation.view.profile.ProfileActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private lateinit var navGraph: NavGraph

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setNavigation()

        binding.mainProfileIcon.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }

    private fun setNavigation() {
        val scanAble = intent.getBooleanExtra("scanAble", false)

        navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController
        navGraph = navController.navInflater.inflate(R.navigation.bottom_navigation)

        // start destination 설정하기
        if (scanAble) navGraph.setStartDestination(R.id.outingFragment)
        else navGraph.setStartDestination(R.id.homeFragment)

        // start destination 적용하기
        navController.setGraph(navGraph, null)

        binding.gomsBottomNavigationView.setupWithNavController(navController)
    }

    fun navigateToOuting() {
        navController.navigate(R.id.outingFragment)

        binding.gomsBottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> navController.navigate(R.id.homeFragment)
                R.id.qrScanFragment -> navController.navigate(R.id.qrScanFragment)
                R.id.outingFragment -> navController.navigate(R.id.outingFragment)
                else -> return@setOnItemSelectedListener false
            }
            return@setOnItemSelectedListener true
        }
    }
}