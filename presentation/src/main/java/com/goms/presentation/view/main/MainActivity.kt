package com.goms.presentation.view.main

import android.Manifest
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
import com.goms.domain.data.profile.ProfileResponseData
import com.goms.presentation.R
import com.goms.presentation.databinding.ActivityMainBinding
import com.goms.presentation.utils.checkUserIsAdmin
import com.goms.presentation.view.profile.ProfileActivity
import com.goms.presentation.viewmodel.ProfileViewModel
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
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

    companion object {
        private var instance: MainActivity? = null

        fun getInstance(): MainActivity {
            return instance as MainActivity
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme()

        instance = this     // 현재 mainActivity의 instance를 가져옴(외부에서 사용하기 위함)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestNotificationPermission()
        if (checkUserIsAdmin(this)) {
            binding.gomsBottomNavigationView.menu.findItem(R.id.qrScanFragment).title = "생성하기"
            binding.gomsBottomNavigationView.menu.findItem(R.id.qrScanFragment).setIcon(R.drawable.qr_code_icon)
        }

        val intentExtra = intent.getSerializableExtra("profile") as ProfileResponseData?
        if (intentExtra == null) {
            lifecycleScope.launch {
                getProfile()
            }
        }
        else {
            response = intentExtra
            binding.mainCircleProfileIcon.load(intentExtra.profileUrl ?: R.drawable.user_profile)
        }

        setNavigation()
        binding.mainCircleProfileIcon.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java)
                .putExtra("profile", response))
        }
    }

    private fun requestNotificationPermission() {
        TedPermission.create()
            .setPermissionListener(object: PermissionListener {
                override fun onPermissionGranted() {}
                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {}
            })
            .setDeniedMessage("알림 권한이 거부되었습니다.\n권한을 설정하려면\n\n[앱 정보] > [권한]에서 설정하세요.")
            .setPermissions(Manifest.permission.POST_NOTIFICATIONS)
            .check()
    }

    private suspend fun getProfile() {
        profileViewModel.getProfileLogic(activity = this)
        profileViewModel.profile.collect { data ->
            if (data != null) {
                response = data
                binding.mainCircleProfileIcon.load(data.profileUrl ?: R.drawable.user_profile)
                setOutingSf(data)
            }
        }
    }

    private fun setOutingSf(data: ProfileResponseData) {
        val sharedPreferences = getSharedPreferences("userOuting", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        if (!sharedPreferences.contains("outingStatus"))
            editor.putBoolean("outingStatus", false)

        editor.putBoolean("outingStatus", data.isOuting)
        editor.apply()
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

    fun navigateComplete() {
        navController.navigate(R.id.scanCompleteFragment)
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