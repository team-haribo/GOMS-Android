package com.goms.presentation.view.home

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.presentation.R
import com.example.presentation.databinding.FragmentHomeBinding
import com.goms.domain.data.profile.ProfileResponseData
import com.goms.presentation.utils.apiErrorHandling
import com.goms.presentation.utils.checkUserIsAdmin
import com.goms.presentation.view.home.component.HomeItemCard
import com.goms.presentation.view.home.component.LateRankEmptyScreen
import com.goms.presentation.view.main.MainActivity
import com.goms.presentation.view.manage.StudentManageActivity
import com.goms.presentation.view.profile.ProfileActivity
import com.goms.presentation.view.qr_scan.capture.QrCodeActivity
import com.goms.presentation.viewmodel.LateViewModel
import com.goms.presentation.viewmodel.OutingViewModel
import com.goms.presentation.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val profileViewModel by viewModels<ProfileViewModel>()
    private val outingViewModel by viewModels<OutingViewModel>()
    private val lateViewModel by viewModels<LateViewModel>()
    private lateinit var binding: FragmentHomeBinding

    private lateinit var mainActivity: MainActivity
    private var response: ProfileResponseData? = null

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?)
    : View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        homeLogic()
        val sharedPreferences = context?.getSharedPreferences("userOuting", MODE_PRIVATE)
        val outingStatus = sharedPreferences?.getBoolean("outingStatus", false) as Boolean
        binding.mainOutingButton.text = if(checkUserIsAdmin(requireContext())) "QR 생성하기"
        else if (outingStatus) "복귀하기" else "외출하기"

        binding.mainOutingButton.setOnClickListener {
            if (checkUserIsAdmin(requireContext())) {
                mainActivity.navigateToQrScan()
            } else startActivity(Intent(context, QrCodeActivity::class.java))
        }

        if(checkUserIsAdmin(requireContext()))
            binding.gomsMainTitleText.text = "간편하게\n수요 외출제를\n관리해 보세요!"
        else binding.gomsMainTitleText.text = "간편하게\n수요 외출제를\n이용해 보세요!"

        binding.homeOutingStudentCardView.setOnClickListener {
            mainActivity.navigateToOuting()
        }

        setBottomCard()
        binding.mainProfileCardViewStudent.setOnClickListener {
            startActivity(Intent(context, ProfileActivity::class.java)
                .putExtra("profile", response))
        }
        binding.mainProfileCardViewAdmin.setOnClickListener {
            startActivity(Intent(context, StudentManageActivity::class.java))
        }

        return binding.root
    }

    private fun homeLogic() {
        setLoading()
        lifecycleScope.launch {
            apiErrorHandling(
                context = context,
                logic = { setProfile() }
            )
        }

        lifecycleScope.launch {
            apiErrorHandling(
                context = context,
                logic = { getOutingCount() }
            )
        }

        lifecycleScope.launch {
            apiErrorHandling(
                context = context,
                logic = { setLateRankList() }
            )
        }
    }

    private fun setLoading() {
        lifecycleScope.launch {
            lateViewModel.isLoading.collect { loading ->
                if (loading) binding.homeLoadingIndicator.root.visibility = View.VISIBLE
                else binding.homeLoadingIndicator.root.visibility = View.GONE
            }
        }
    }

    private fun setBottomCard() {
        if (checkUserIsAdmin(requireContext())) {
            binding.mainProfileCardViewStudent.visibility = View.GONE
            binding.mainProfileCardViewAdmin.visibility = View.VISIBLE
        } else {
            binding.mainProfileCardViewStudent.visibility = View.VISIBLE
            binding.mainProfileCardViewAdmin.visibility = View.GONE
        }
    }

    private suspend fun getOutingCount() {
        outingViewModel.outingCount()
        outingViewModel.outingCount.collect { people ->
            binding.currentStudentOutingText.setContent {
                StudentOutingText(people!!.outingCount)
            }
        }
    }

    private suspend fun setLateRankList() {
        lateViewModel.getLateRanking()
        lateViewModel.lateRanking.collect { list ->
            binding.lateRankingLazyRow.setContent {
                if (list != null) {
                    if (list.isEmpty()) LateRankEmptyScreen()
                    else LateLazyRow(list)
                }
            }
        }
    }

    private suspend fun setProfile() {
        profileViewModel.getProfileLogic()
        profileViewModel.profile.collect { data ->
            response = data

            binding.mainProfileCardUserNameText.text = data?.name
            binding.mainProfileCardStudentNumberText.text =
                "${data?.studentNum?.grade}학년 ${data?.studentNum?.classNum}반 ${data?.studentNum?.number}번"
            binding.mainProfileCardUserCircleImage.load(data?.profileUrl ?: R.drawable.user_profile)
        }
    }

    @Composable
    private fun StudentOutingText(outingCount: Int) {
        val mainColor = if (checkUserIsAdmin(requireContext()))
            colorResource(id = R.color.goms_main_color_admin)
        else colorResource(id = R.color.goms_main_color_student)

        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(color = mainColor)
                ) {
                    append(outingCount.toString())
                }
                append("명이 외출중이에요!")
            },
            style = TextStyle(
                color = Color.Black,
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.sf_pro_text_medium, FontWeight.Medium))
            )
        )
    }

    @Composable
    private fun LateLazyRow(list: List<ProfileResponseData>) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 25.dp, end = 25.dp, top = 5.dp, bottom = 5.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(horizontal = 1.dp)
        ) {
            items(list) { item ->
                Box(modifier = Modifier.shadow(
                    elevation = 1.dp,
                    shape = RoundedCornerShape(10.dp)
                )) {
                    HomeItemCard(item)
                }
            }

        }
    }
}