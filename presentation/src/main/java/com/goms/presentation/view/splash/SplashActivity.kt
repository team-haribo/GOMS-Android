package com.goms.presentation.view.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.goms.presentation.R
import com.goms.presentation.view.main.MainActivity
import com.goms.presentation.view.sign_in.SignInActivity
import com.goms.presentation.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private val splashViewModel by viewModels<SplashViewModel>()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        sharedPreferences = getSharedPreferences("userOuting", MODE_PRIVATE)
        if (!sharedPreferences.contains("outingStatus"))
            setSharedPreference()

        Handler(Looper.getMainLooper()).postDelayed({
            if (checkIsInterConnected()) {
                splashViewModel.checkIsLogin()
                observeLogin()
            } else Toast.makeText(this, "인터넷 없음", Toast.LENGTH_SHORT).show()
        }, 1000)
    }

    private fun setSharedPreference() {
        sharedPreferences.edit()
            .putBoolean("outingStatus", false)
            .apply()
    }

    private fun observeLogin() {
        splashViewModel.isLogin.observe(this) {
            if (it != null) {
                when(it) {
                    true -> {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                    false -> {
                        startActivity(Intent(this, SignInActivity::class.java))
                        finish()
                    }
                }
            }
        }
    }

    // 인터넷 연결 확인하기
    private fun checkIsInterConnected(): Boolean {
        val connectivityManager: ConnectivityManager =
            this.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(networkInfo) ?: return false
        val result = when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }

        return result
    }
}