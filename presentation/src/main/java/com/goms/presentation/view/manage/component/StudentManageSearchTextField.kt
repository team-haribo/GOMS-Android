package com.goms.presentation.view.manage.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
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
import com.example.presentation.R

@Composable
fun StudentManageSearchTextField(
    hint: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    val studentManageTextFieldFont = FontFamily(
        Font(R.font.sf_pro_text_regular, FontWeight.Normal)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
            .padding(15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            value = value,
            singleLine = true,
            textStyle = TextStyle(
                fontFamily = studentManageTextFieldFont
            ),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = hint,
                            color = colorResource(id = R.color.goms_third_color_gray)
                        )
                    }
                    innerTextField()
                }
            },
            onValueChange = onValueChange
        )
        
        Icon(
            modifier = Modifier
                .size(20.dp)
                .clickable {
                    Log.d("TAG", "StudentManageSearchTextField: i click")
                },
            imageVector = Icons.Default.Search,
            tint = colorResource(id = R.color.goms_third_color_gray),
            contentDescription = "search student icon"
        )
    }
}