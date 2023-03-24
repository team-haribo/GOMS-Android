package com.example.gsm_goms.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.ui.unit.dp
import com.example.gsm_goms.R
import com.example.gsm_goms.databinding.ActivityMainBinding
import com.msg.gauthsignin.GAuth
import com.msg.gauthsignin.GAuthSigninWebView
import com.msg.gauthsignin.component.GAuthButton
import com.msg.gauthsignin.component.utils.Types

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}