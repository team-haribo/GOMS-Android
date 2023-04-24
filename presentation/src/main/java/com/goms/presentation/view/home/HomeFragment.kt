package com.goms.presentation.view.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
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
import com.example.presentation.R
import com.example.presentation.databinding.FragmentHomeBinding
import com.goms.presentation.view.home.component.HomeItemCard
import com.goms.presentation.view.main.MainActivity
import com.goms.presentation.view.profile.ProfileActivity
import com.goms.presentation.view.qr_scan.capture.QrCodeActivity

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?)
    : View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        binding.currentStudentOutingText.setContent {
            StudentOutingText()
        }

        binding.mainOutingButton.setOnClickListener {
            startActivity(Intent(context, QrCodeActivity::class.java))
        }

        binding.lateRankingLazyRow.setContent {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 5.dp, bottom = 5.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(horizontal = 5.dp)
            ) {
                items(5) {
                    Box(modifier = Modifier.shadow(
                        elevation = 3.dp,
                        shape = RoundedCornerShape(10.dp)
                    )) {
                        HomeItemCard()
                    }
                }
            }
        }

        binding.homeOutingStudentCardView.setOnClickListener {
            val mainActivity = activity as MainActivity
            mainActivity.navigateToOuting()
        }

        binding.mainProfileCardView.setOnClickListener {
            startActivity(Intent(context, ProfileActivity::class.java))
        }

        return binding.root
    }

    @Composable
    private fun StudentOutingText() {
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(color = colorResource(id = R.color.goms_main_color_student))
                ) {
                    append("48")
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
}