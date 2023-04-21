package com.goms.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
import com.example.presentation.R
import com.example.presentation.databinding.ActivityMainBinding
import com.goms.presentation.view.component.MainItemCard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.currentStudentOutingText.setContent {
            StudentOutingText()
        }

        binding.lateRankingLazyRow.setContent {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 25.dp, end = 25.dp)
            ) {
                items(10) {
                    Box(modifier = Modifier.padding(end = 10.dp)) {
                        MainItemCard()
                    }
                }
            }
        }
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