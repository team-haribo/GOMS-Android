package com.goms.presentation.view.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.presentation.R
import com.example.presentation.databinding.ActivityMainBinding
import com.goms.presentation.view.profile.ProfileActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

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

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.bottom_navigation)

        // start destination 설정하기
        if (scanAble) navGraph.setStartDestination(R.id.outingFragment)
        else navGraph.setStartDestination(R.id.homeFragment)

        // start destination 적용하기
        navController.setGraph(navGraph, null)

        binding.gomsBottomNavigationView.setupWithNavController(navController)
    }
}