package com.goms.presentation.view.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.presentation.R
import com.skydoves.landscapist.coil.CoilImage

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeItemCard() {
    val homeCardFont = FontFamily(
        Font(R.font.sf_pro_text_regular, FontWeight.Normal),
        Font(R.font.sf_pro_text_medium, FontWeight.Medium)
    )

    Card(
        modifier = Modifier
            .width(110.dp)
            .fillMaxHeight()
            .clip(RoundedCornerShape(10.dp)),
        onClick = { /*TODO*/ }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CoilImage(
                modifier = Modifier.size(40.dp),
                imageModel = { R.drawable.user_profile }
            )

            Text(
                modifier = Modifier.padding(top = 15.dp),
                text = "선민재",
                style = TextStyle(
                    fontFamily = homeCardFont,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                )
            )

            Text(
                modifier = Modifier.padding(top = 7.dp),
                text = "3111",
                style = TextStyle(
                    fontFamily = homeCardFont,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp
                )
            )
        }
    }
}