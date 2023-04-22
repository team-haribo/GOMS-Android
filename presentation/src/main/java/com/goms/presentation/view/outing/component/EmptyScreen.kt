package com.goms.presentation.view.outing.component

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
import com.example.presentation.R

@Composable
fun EmptyScreen() {
    val emptyScreenFont = FontFamily(Font(R.font.sf_pro_text_regular, FontWeight.Normal))

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.alpha(0.5f),
            painter = painterResource(id = R.drawable.working_re_ddwy),
            contentDescription = "empty image"
        )
        Text(
            modifier = Modifier.padding(top = 15.dp),
            text = "다들 바쁜가봐요..!",
            style = TextStyle(
                fontFamily = emptyScreenFont,
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp
            ),
            color = colorResource(id = R.color.goms_empty_page_color)
        )
    }
}