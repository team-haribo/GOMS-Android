package com.goms.presentation.view.profile

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.presentation.R
import com.example.presentation.databinding.ActivityProfileBinding
import com.goms.domain.data.profile.ProfileResponseData
import com.goms.presentation.view.sign_in.SignInActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    private var profileData: ProfileResponseData? = null

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        profileData = intent.getSerializableExtra("profile") as ProfileResponseData
        setView()

        binding.backToMainImage.setOnClickListener { finish() }

        binding.logoutView.setOnClickListener {
            val tokenInfo = this.getSharedPreferences("token", MODE_PRIVATE)
            tokenInfo.edit().clear().apply()

            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }
    }

    private fun setView() {
        val grade = profileData?.studentNum?.grade
        val classNum = profileData?.studentNum?.classNum
        val number =
            if (profileData?.studentNum?.number!! < 10) "0${profileData?.studentNum?.number}"
            else profileData?.studentNum?.number

        binding.profileUserName.text = profileData?.name
        binding.profileStudentNumber.text = "$grade$classNum$number"
        binding.profileCardUserNameText.text = profileData?.name
        binding.profileCardUserGradeText.text = grade.toString()
        binding.profileCardUserClassText.text = classNum.toString()
        binding.profileCardUserNumberText.text = profileData?.studentNum?.number.toString()
        binding.profileCardUserLateCountText.text = profileData?.lateCount.toString()
        binding.profileUserCircleImage.load(profileData?.profileUrl ?: R.drawable.user_profile)
    }
}