package com.goms.presentation.utils

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.goms.domain.exception.FailAccessTokenException
import com.goms.domain.exception.NotCouncilException
import com.goms.domain.exception.OtherException
import com.goms.domain.exception.QrCodeExpiredException
import com.goms.domain.exception.ServerException
import com.goms.domain.exception.UserIsBlackListException
import com.goms.domain.exception.UserNotFoundException
import com.goms.presentation.view.home.dialog.GomsBlackListDialog
import com.goms.presentation.view.main.MainActivity
import com.goms.presentation.view.qr_scan.capture.QrCodeActivity

suspend fun apiErrorHandling(
    context: Context?,
    logic: suspend () -> Unit
) {
    try {
        logic()
    } catch (e: Exception) {
        when(e) {
            is NotCouncilException -> Toast.makeText(context, "학생회 권한인 학생만 요청 가능합니다.", Toast.LENGTH_SHORT).show()
            is UserNotFoundException -> Toast.makeText(context, "존재하지 않은 사용자입니다.", Toast.LENGTH_SHORT).show()
            is FailAccessTokenException -> Toast.makeText(context, "엑세스 토큰이 유효하지 않습니다.", Toast.LENGTH_SHORT).show()
            is UserIsBlackListException -> {
                val activity = context as QrCodeActivity
                val dialog = GomsBlackListDialog()
                dialog.show(activity.supportFragmentManager, "isBlackList")
            }
            is QrCodeExpiredException -> {
                Toast.makeText(context, "만료된 QR Code입니다.", Toast.LENGTH_SHORT).show()
                toMainActivity(context as QrCodeActivity)
            }
            is IllegalArgumentException -> {
                Toast.makeText(context, "유효하지 않은 Qr Code입니다.", Toast.LENGTH_SHORT).show()
                toMainActivity(context as QrCodeActivity)
            }
            is ServerException -> Toast.makeText(context, "서버 에러, 관리자에게 문의하세요.", Toast.LENGTH_SHORT).show()
            is OtherException -> Toast.makeText(context, "알 수 없는 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
        }
    }
}

private fun toMainActivity(activity: QrCodeActivity) {
    activity.startActivity(Intent(activity, MainActivity::class.java))
    activity.finish()
}