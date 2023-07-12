package com.goms.presentation.view.outing.component

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.goms.domain.data.user.UserResponseData
import com.goms.presentation.R
import com.goms.presentation.utils.checkUserIsAdmin
import com.skydoves.landscapist.coil.CoilImage
import java.util.*

@Composable
fun OutingStudentCard(
    item: UserResponseData,
    onClick: (UUID) -> Unit,
    context: Context
) {

    val outingCardFont = FontFamily(
        Font(R.font.sf_pro_text_regular, FontWeight.Normal)
    )
    val timeFont = FontFamily(
        Font(R.font.sf_pro_text_light, FontWeight.Light)
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
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape),
                imageModel = { item.profileUrl ?: R.drawable.user_profile }
            )

            Column(modifier = Modifier.padding(start = 25.dp)) {
                Text(
                    text = item.name,
                    style = TextStyle(
                        fontFamily = outingCardFont,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp
                    ),
                    color = Color.Black
                )

                val studentInfo = item.studentNum
                Text(
                    text = "${studentInfo.grade}학년 ${studentInfo.classNum}반 ${studentInfo.number}번",
                    style = TextStyle(
                        fontFamily = outingCardFont,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp
                    ),
                    color = colorResource(id = R.color.goms_main_color_gray)
                )
            }

            Text(
                modifier = Modifier.padding(top = 35.dp, start = 6.dp),
                style = TextStyle(
                    fontFamily = timeFont,
                    fontSize = 12.sp
                ),
                color = Color(0x4D000000),
                text = item.createdTime
            )

            Spacer(modifier = Modifier.weight(1f))

            if (checkUserIsAdmin(context)) {
                Image(
                    modifier = Modifier
                        .padding(end = 20.dp)
                        .clickable { onClick(item.accountIdx) }
                        .align(Alignment.CenterVertically)
                        .wrapContentSize(),
                    contentDescription = "Delete outing student icon",
                    painter = painterResource(id = R.drawable.delete_button_bg)
                )
            }
        }
    }
}