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
import com.goms.presentation.viewmodel.NotificationViewModel
import com.goms.presentation.viewmodel.ProfileViewModel
import com.goms.presentation.viewmodel.SplashViewModel
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private val splashViewModel by viewModels<SplashViewModel>()
    private val profileViewModel by viewModels<ProfileViewModel>()
    private val notificationViewModel by viewModels<NotificationViewModel>()

    private lateinit var userOutingSP: SharedPreferences

    private lateinit var appUpdateManager: AppUpdateManager
    companion object {
        const val REQUEST_CODE = 12
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        appUpdateManager = AppUpdateManagerFactory.create(this)
        Handler(Looper.getMainLooper()).postDelayed({
            if (checkIsInterConnected()) {
                setInAppUpdate()

                userOutingSP = getSharedPreferences("userOuting", MODE_PRIVATE)
                if (!userOutingSP.contains("outingStatus"))
                    initSharedPreference()
            } else Toast.makeText(this, "인터넷 없음", Toast.LENGTH_SHORT).show()
        }, 1000)
    }

    private fun initSharedPreference() {
        userOutingSP.edit()
            .putBoolean("outingStatus", false)
            .apply()
    }

    private fun observeLogin() {
        splashViewModel.isLogin.observe(this) {
            if (it != null) {
                when(it) {
                    true -> {
                        val intent = Intent(this, MainActivity::class.java)
                        profileViewModel.getProfileLogic()
                        saveRole(intent)
                        initNotification()
                    }
                    false -> {
                        startActivity(Intent(this, SignInActivity::class.java))
                        finish()
                    }
                }
            }
        }
    }

    private fun saveRole(intent: Intent) {
        lifecycleScope.launch {
            profileViewModel.profile.collect { profile ->
                if (profile != null) {
                    intent.putExtra("profile", profile)

                    val authSf = getSharedPreferences("authority", MODE_PRIVATE)
                    authSf.edit().let {
                        it.putString("role", profile.authority)
                        it.apply()
                    }

                    startActivity(intent)
                    finish()
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

    private fun initNotification() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val deviceTokenSF = getSharedPreferences("deviceToken", MODE_PRIVATE)
                val token = task.result
                if (deviceTokenSF.getString("device", "") == token) {
                    notificationViewModel.setNotification(token)
                    setNotificationLogic(token)
                }
            }
        }
    }

    private fun setNotificationLogic(token: String) {
        lifecycleScope.launch {
            notificationViewModel.setNotification.collect {
                val deviceTokenSF = getSharedPreferences("deviceToken", MODE_PRIVATE)
                deviceTokenSF.edit().putString("device", token).apply()
            }
        }
    }

    private fun setInAppUpdate() {
        val appUpdateTask = appUpdateManager.appUpdateInfo

        appUpdateTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                appUpdateManager.startUpdateFlowForResult(appUpdateInfo, IMMEDIATE, this, REQUEST_CODE)
            } else {
                splashViewModel.checkIsLogin()
                observeLogin()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE) {
            if (resultCode != RESULT_OK) {
                // app 종료
                moveTaskToBack(true)
                finishAndRemoveTask()
                android.os.Process.killProcess(0)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        appUpdateManager.appUpdateInfo.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                appUpdateManager.startUpdateFlowForResult(appUpdateInfo, IMMEDIATE, this, REQUEST_CODE)
            }
        }
    }
}