package com.goms.presentation.view.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import coil.load
import com.example.presentation.R
import com.example.presentation.databinding.ActivityMainBinding
import com.goms.domain.data.profile.ProfileResponseData
import com.goms.presentation.utils.checkUserIsAdmin
import com.goms.presentation.view.profile.ProfileActivity
import com.goms.presentation.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val profileViewModel by viewModels<ProfileViewModel>()

    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private lateinit var navGraph: NavGraph

    private var response: ProfileResponseData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme()


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (checkUserIsAdmin(this)) {
            binding.gomsBottomNavigationView.menu.findItem(R.id.qrScanFragment).title = "생성하기"
            binding.gomsBottomNavigationView.menu.findItem(R.id.qrScanFragment).setIcon(R.drawable.qr_code_icon)
        }

        setNavigation()
        setProfile()
        binding.mainCircleProfileIcon.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java)
                .putExtra("profile", response))
        }
    }

    private fun setProfile() {
        lifecycleScope.launch {
            profileViewModel.getProfileLogic()
            profileViewModel.profile.collect { data ->
                response = data
                binding.mainCircleProfileIcon.load(data?.profileUrl ?: R.drawable.user_profile)
            }
        }
    }

    private fun setNavigation() {
        navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController
        navGraph = navController.navInflater.inflate(R.navigation.bottom_navigation)

        // start destination 설정하기
        navGraph.setStartDestination(R.id.homeFragment)

        // start destination 적용하기
        navController.setGraph(navGraph, null)

        binding.gomsBottomNavigationView.setupWithNavController(navController)
    }

    fun navigateToOuting() {
        navController.navigate(R.id.outingFragment)

        navigationItemSelectListener()
    }

    fun navigateToQrScan() {
        navController.navigate(R.id.qrScanFragment)

        navigationItemSelectListener()
    }

    private fun navigationItemSelectListener() {
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

    private fun setTheme() {
        val authorityPreferences = getSharedPreferences("authority", MODE_PRIVATE)
        val role = authorityPreferences.getString("role", "").toString()
        if (role == "ROLE_STUDENT") super.setTheme(R.style.Theme_GSM_GOMS)
        else super.setTheme(R.style.Theme_GSM_GOMS_ADMIN)
    }
}