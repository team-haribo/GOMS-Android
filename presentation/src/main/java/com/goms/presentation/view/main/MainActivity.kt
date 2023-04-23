package com.goms.presentation.view.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.presentation.R
import com.example.presentation.databinding.ActivityMainBinding
import com.goms.presentation.view.home.HomeFragment
import com.goms.presentation.view.outing.OutingFragment
import com.goms.presentation.view.profile.ProfileActivity
import com.goms.presentation.view.qr_scan.QrScanFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().add(R.id.fragment_container, HomeFragment()).commit()

        binding.gomsBottomNavigationView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.home_fragment -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, HomeFragment()).commit()
                }
                R.id.qr_scan_fragment -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, QrScanFragment()).commit()
                }
                R.id.outing_fragment -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, OutingFragment()).commit()
                }
                else -> return@setOnItemSelectedListener false
            }

            return@setOnItemSelectedListener true
        }

        binding.mainProfileIcon.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }
}