package com.goms.presentation.view.manage.component

import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.example.presentation.R
import com.example.presentation.databinding.ActivityStudentManageBinding
import com.goms.domain.data.user.UserResponseData
import com.goms.presentation.view.manage.StudentManageActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun StudentManageCard(
    binding: ActivityStudentManageBinding,
    activity: StudentManageActivity,
    item: UserResponseData
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
            CoilImage(
                modifier = Modifier.size(50.dp)
                    .clip(CircleShape),
                imageModel = { item.profileUrl ?: R.drawable.user_profile }
            )

            Column(
                modifier = Modifier
                    .padding(start = 15.dp)
                    .weight(1f)
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
                        binding.manageStudentBottomSheetView.visibility = View.GONE
                        binding.modifyRoleBottomSheetView.visibility = View.VISIBLE

                        val bottomSheet = binding.bottomSheetView
                        val behavior = BottomSheetBehavior.from(bottomSheet)
                        activity.setBottomSheet(behavior)
                    },
                painter = painterResource(id = R.drawable.pencil_icon),
                contentDescription = "student manage icon"
            )
        }
    }
}