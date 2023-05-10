package com.goms.presentation.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE

// 역할이 admin인지 확인합니다.
fun getUserIsAdmin(context: Context): Boolean {
    val authorityPreferences = context.getSharedPreferences("authority", MODE_PRIVATE)
    val role = authorityPreferences.getString("role", "").toString()

    return role != "ROLE_STUDENT"
}