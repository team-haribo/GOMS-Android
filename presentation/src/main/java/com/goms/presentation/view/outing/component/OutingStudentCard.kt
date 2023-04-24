package com.goms.presentation.view.outing.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
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
fun OutingStudentCard() {
    val outingCardFont = FontFamily(
        Font(R.font.sf_pro_text_regular, FontWeight.Normal)
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clip(RoundedCornerShape(10.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 25.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CoilImage(
                modifier = Modifier.size(50.dp),
                imageModel = { R.drawable.user_profile }
            )

            Column(modifier = Modifier.padding(start = 25.dp)) {
                Text(
                    text = "김성길",
                    style = TextStyle(
                        fontFamily = outingCardFont,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp
                    ),
                    color = Color.Black
                )
                
                Text(
                    text = "3학년 4반 2번",
                    style = TextStyle(
                        fontFamily = outingCardFont,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp
                    ),
                    color = colorResource(id = R.color.goms_main_color_gray)
                )
            }
        }
    }
}