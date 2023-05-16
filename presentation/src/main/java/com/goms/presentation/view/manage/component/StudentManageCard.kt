package com.goms.presentation.view.manage.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.presentation.R
import com.goms.domain.data.council.response.UserListResponseData
import com.skydoves.landscapist.coil.CoilImage
import java.util.UUID

@Composable
fun StudentManageCard(
    item: UserListResponseData,
    iconClick: (UUID) -> Unit
) {
    val studentManageCardFont = FontFamily(
        Font(R.font.sf_pro_text_regular, FontWeight.Normal)
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .clip(RoundedCornerShape(10.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (item.authority != "ROLE_STUDENT")
                StudentProfileRole(item = item)
            else StudentProfile(item = item)

            Column(
                modifier = Modifier
                    .padding(start = 15.dp)
                    .weight(1f),
            ) {
                Text(
                    text = item.name,
                    style = TextStyle(
                        fontFamily = studentManageCardFont,
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                )

                val studentNum = item.studentNum
                Text(
                    text = "${studentNum.grade}학년 ${studentNum.classNum}반 ${studentNum.number}번",
                    style = TextStyle(
                        fontFamily = studentManageCardFont,
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.goms_main_color_gray)
                    )
                )
            }

            Image(
                modifier = Modifier
                    .align(Alignment.Top)
                    .padding(top = 5.dp)
                    .clickable {
                        iconClick(item.accountIdx)
                    },
                painter = painterResource(id = R.drawable.pencil_icon),
                contentDescription = "student manage icon"
            )
        }
    }
}

@Composable
fun StudentProfileRole(item: UserListResponseData) {
//    val authority = item.authority
    val profileMainColor = if (item.authority == "ROLE_STUDENT_COUNCIL")
        colorResource(id = R.color.goms_main_color_admin)
    else colorResource(id = R.color.goms_black_list_color_red)

    Box(
        modifier = Modifier
            .width(50.dp)
            .height(58.dp)
    ) {
        CoilImage(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape),
            imageModel = { item.profileUrl ?: R.drawable.user_profile }
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(
                    color = Color.White,
                    shape = CircleShape
                )
                .clip(CircleShape)
                .border(
                    width = 1.dp,
                    color = profileMainColor,
                    shape = CircleShape
                )
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 2.dp, bottom = 2.dp),
                text = "학생회",
                style = TextStyle(
                    fontSize = 9.sp,
                    color = profileMainColor
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun StudentProfile(item: UserListResponseData) {
    CoilImage(
        modifier = Modifier
            .size(50.dp)
            .clip(CircleShape),
        imageModel = { item.profileUrl ?: R.drawable.user_profile }
    )
}

@Preview
@Composable
fun TestText() {

}