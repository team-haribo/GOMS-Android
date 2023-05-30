package com.goms.presentation.view.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.goms.presentation.R

@Composable
fun LateRankEmptyScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val screenFont = FontFamily(
            Font(R.font.sf_pro_text_regular, FontWeight.Normal)
        )

        Image(
            modifier = Modifier.alpha(0.5f),
            painter = painterResource(id = R.drawable.mind_fulness),
            contentDescription = "late ranking empty image"
        )
        Text(
            modifier = Modifier.padding(top = 10.dp),
            text = "아직 고요하네요!",
            style = TextStyle(
                fontSize = 14.sp,
                color = colorResource(id = R.color.goms_second_color_gray),
                fontFamily = screenFont
            )
        )
    }
}