package com.goms.presentation.utils

import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.goms.domain.exception.FailAccessTokenException
import com.goms.domain.exception.InternetConnectException
import com.goms.domain.exception.NotCouncilException
import com.goms.domain.exception.OtherException
import com.goms.domain.exception.QrCodeExpiredException
import com.goms.domain.exception.ServerException
import com.goms.domain.exception.UserIsBlackListException
import com.goms.domain.exception.UserNotFoundException
import com.goms.presentation.utils.dialog.GomsNetworkDialog
import com.goms.presentation.view.home.dialog.GomsBlackListDialog
import com.goms.presentation.view.main.MainActivity
import com.goms.presentation.view.qr_scan.capture.QrCodeActivity

suspend fun apiErrorHandling(
    activity: AppCompatActivity,
    logic: suspend () -> Unit
) {
    try {
        logic()
    } catch (e: Exception) {
        when(e) {
            is NotCouncilException -> Toast.makeText(activity, "학생회 권한인 학생만 요청 가능합니다.", Toast.LENGTH_SHORT).show()
            is UserNotFoundException -> Toast.makeText(activity, "존재하지 않은 사용자입니다.", Toast.LENGTH_SHORT).show()
            is FailAccessTokenException -> Toast.makeText(activity, "엑세스 토큰이 유효하지 않습니다.", Toast.LENGTH_SHORT).show()
            is UserIsBlackListException -> {
                val qrCodeActivity = activity as QrCodeActivity
                val dialog = GomsBlackListDialog()
                dialog.show(qrCodeActivity.supportFragmentManager, "isBlackList")
            }
            is QrCodeExpiredException -> {
                Toast.makeText(activity, "만료된 QR Code입니다.", Toast.LENGTH_SHORT).show()
                toMainActivity(activity as QrCodeActivity)
            }
            is IllegalArgumentException -> {
                Toast.makeText(activity, "유효하지 않은 Qr Code입니다.", Toast.LENGTH_SHORT).show()
                toMainActivity(activity as QrCodeActivity)
            }
            is InternetConnectException -> {
                val dialog = GomsNetworkDialog(
                    retryLogic = {
                        apiErrorHandling(
                            activity = activity,
                            logic = logic
                        )
                    }
                )
                if (!dialog.isAdded) dialog.show(activity.supportFragmentManager, "network")
            }
            is ServerException -> Toast.makeText(activity, "서버 에러, 관리자에게 문의하세요.", Toast.LENGTH_SHORT).show()
            is OtherException -> Toast.makeText(activity, "알 수 없는 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
        }
    }
}

private fun toMainActivity(activity: QrCodeActivity) {
    activity.startActivity(Intent(activity, MainActivity::class.java))
    activity.finish()
}