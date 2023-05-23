package com.goms.presentation.utils

import android.content.Context
import android.widget.Toast
import com.goms.domain.exception.FailAccessTokenException
import com.goms.domain.exception.NotCouncilException
import com.goms.domain.exception.ServerException
import com.goms.domain.exception.UserIsBlackListException
import com.goms.domain.exception.UserNotFoundException

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
            is UserIsBlackListException -> Toast.makeText(context, "외출 금지 상태입니다.", Toast.LENGTH_SHORT).show()
            is ServerException -> Toast.makeText(context, "서버 에러, 관리자에게 문의하세요.", Toast.LENGTH_SHORT).show()
        }
    }
}