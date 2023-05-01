package com.goms.presentation.view.profile

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.presentation.R
import com.example.presentation.databinding.ActivityProfileBinding
import com.goms.domain.data.profile.response.ProfileResponseData
import com.goms.presentation.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {
    private val profileViewModel by viewModels<ProfileViewModel>()
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            profileViewModel.getProfileLogic()
            profileViewModel.isLoading.collect { isLoading ->
                if (isLoading) {
                    binding.profileActivityView.visibility = View.GONE
                    binding.profileProgressBar.visibility = View.VISIBLE
                } else {
                    binding.profileProgressBar.visibility = View.GONE
                    profileViewModel.profile.collect { response ->
                        if (response != null)
                            setView(response)
                    }
                }
            }
        }

        binding.backToMainImage.setOnClickListener { finish() }
    }

    private fun setView(response: ProfileResponseData?) {
        val grade = response?.studentNum?.grade
        val classNum = response?.studentNum?.classNum
        val number = response?.studentNum?.number

        binding.profileUserName.text = response?.name
        binding.profileStudentNumber.text = "$grade$classNum$number"
        binding.profileCardUserNameText.text = response?.name
        binding.profileCardUserGradeText.text = grade.toString()
        binding.profileCardUserClassText.text = classNum.toString()
        binding.profileCardUserNumberText.text = number.toString()
        binding.profileCardUserLateCountText.text = response?.lateCount.toString()
        binding.profileUserImage.load(response?.profileUrl ?: R.drawable.user_profile)
    }
}