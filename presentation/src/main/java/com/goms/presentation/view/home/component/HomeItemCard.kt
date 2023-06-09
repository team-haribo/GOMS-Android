package com.goms.presentation.view.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.goms.domain.data.late.LateUserResponseData
import com.goms.presentation.R
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun HomeItemCard(item: LateUserResponseData) {
    val homeCardFont = FontFamily(
        Font(R.font.sf_pro_text_regular, FontWeight.Normal),
        Font(R.font.sf_pro_text_medium, FontWeight.Medium)
    )
    val configuration  = LocalConfiguration.current
    val itemWidth = (configuration.screenWidthDp - 70) / 3

    Card(
        modifier = Modifier
            .width(itemWidth.dp)
            .fillMaxHeight()
            .clip(RoundedCornerShape(10.dp)),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CoilImage(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                imageModel = { item.profileUrl ?: R.drawable.user_profile }
            )

            Text(
                modifier = Modifier.padding(top = 15.dp),
                text = item.name ?: "❓❓❓",
                style = TextStyle(
                    fontFamily = homeCardFont,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                )
            )

            val studentInfo = item.studentNum
            val studentNum =
                if (studentInfo.number < 10) "0${studentInfo.number}"
                else studentInfo.number
            Text(
                modifier = Modifier.padding(top = 7.dp),
                text = "${studentInfo.grade}${studentInfo.classNum}$studentNum" ?: "0000",
                style = TextStyle(
                    fontFamily = homeCardFont,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp
                )
            )
        }
    }
}