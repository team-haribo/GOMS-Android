package com.goms.presentation.view.profile

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.presentation.R
import com.example.presentation.databinding.ActivityProfileBinding
import com.goms.domain.data.profile.response.ProfileResponseData
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

        profileData = intent.getSerializableExtra("profile", ProfileResponseData::class.java)
        setView()

        binding.backToMainImage.setOnClickListener { finish() }
    }

    private fun setView() {
        val grade = profileData?.studentNum?.grade
        val classNum = profileData?.studentNum?.classNum
        val number = profileData?.studentNum?.number

        binding.profileUserName.text = profileData?.name
        binding.profileStudentNumber.text = "$grade$classNum$number"
        binding.profileCardUserNameText.text = profileData?.name
        binding.profileCardUserGradeText.text = grade.toString()
        binding.profileCardUserClassText.text = classNum.toString()
        binding.profileCardUserNumberText.text = number.toString()
        binding.profileCardUserLateCountText.text = profileData?.lateCount.toString()
        binding.profileUserImage.load(profileData?.profileUrl ?: R.drawable.user_profile)
    }
}