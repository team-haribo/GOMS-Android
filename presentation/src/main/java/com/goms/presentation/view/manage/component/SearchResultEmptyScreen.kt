package com.goms.presentation.view.manage.component

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.presentation.R

@Composable
fun SearchResultEmptyScreen() {
    val searchResultEmptyScreenFont = FontFamily(Font(R.font.sf_pro_text_regular, FontWeight.Normal))

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.alpha(0.5f),
            painter = painterResource(id = R.drawable.no_data_re_kwbl),
            contentDescription = "empty image"
        )
        Text(
            modifier = Modifier.padding(top = 15.dp),
            text = "검색 결과를 찾을 수 없어요!",
            style = TextStyle(
                fontFamily = searchResultEmptyScreenFont,
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp
            ),
            color = colorResource(id = R.color.goms_second_color_gray)
        )
    }
}