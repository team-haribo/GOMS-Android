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
import androidx.lifecycle.lifecycleScope
import com.goms.presentation.R
import com.goms.presentation.view.main.MainActivity
import com.goms.presentation.view.sign_in.SignInActivity
import com.goms.presentation.viewmodel.ProfileViewModel
import com.goms.presentation.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private val splashViewModel by viewModels<SplashViewModel>()
    private val profileViewModel by viewModels<ProfileViewModel>()

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var tokenSf: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        sharedPreferences = getSharedPreferences("userOuting", MODE_PRIVATE)
        if (!sharedPreferences.contains("outingStatus"))
            initSharedPreference()

        tokenSf = getSharedPreferences("token", MODE_PRIVATE)
        val refreshToken = tokenSf.getString("refreshToken", "")

        if (!refreshToken.isNullOrEmpty()) {
            splashViewModel.refreshToken(refreshToken)
            saveToken()

            profileViewModel.getProfileLogic()
        }

        Handler(Looper.getMainLooper()).postDelayed({
            if (checkIsInterConnected()) {
                if (refreshToken.isNullOrEmpty()) {
                    splashViewModel.checkIsLogin()
                    observeLogin()
                } else {
                    splashViewModel.checkIsLogin()
                    saveRole()
                    observeLogin()
                }
            } else Toast.makeText(this, "인터넷 없음", Toast.LENGTH_SHORT).show()
        }, 1000)
    }

    private fun initSharedPreference() {
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

    private fun saveRole() {
        lifecycleScope.launch {
            profileViewModel.profile.collect { profile ->
                val authSf = getSharedPreferences("authority", MODE_PRIVATE)
                authSf.edit().let {
                    it.putString("role", profile?.authority)
                    it.apply()
                }
            }
        }
    }

    private fun saveToken() {
        lifecycleScope.launch {
            splashViewModel.token.collect { token ->
                if (token != null) {
                    tokenSf.edit().let {
                        it.putString("accessToken", token.accessToken)
                        it.putString("refreshToken", token.refreshToken)
                        it.putString("accessTokenExp", token.accessTokenExpiredAt)
                        it.putString("refreshTokenExp", token.refreshTokenExpiredAt)
                        it.apply()
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