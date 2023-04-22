package com.goms.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.presentation.R
import com.example.presentation.databinding.ActivityMainBinding
import com.goms.presentation.view.fragment.HomeFragment
import com.goms.presentation.view.fragment.OutingFragment
import com.goms.presentation.view.fragment.QrScanFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
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
                    return@setOnItemSelectedListener true
                }
                R.id.qr_scan_fragment -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, QrScanFragment()).commit()
                    return@setOnItemSelectedListener true
                }
                R.id.outing_fragment -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, OutingFragment()).commit()
                    return@setOnItemSelectedListener true
                }
            }

            return@setOnItemSelectedListener false
        }
    }
}