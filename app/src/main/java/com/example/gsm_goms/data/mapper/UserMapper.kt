package com.example.gsm_goms.data.mapper

import com.example.gsm_goms.data.model.signin.request.SignInRequest
import com.example.gsm_goms.data.model.signin.response.SignInResponse
import com.example.gsm_goms.domain.data.signin.request.SignInRequestData
import com.example.gsm_goms.domain.data.signin.response.SignInResponseData

object UserMapper {
    fun signInResponseToData(signInResponse: SignInResponse): SignInResponseData {
        return SignInResponseData(
            accessToken = signInResponse.accessToken,
            refreshToken = signInResponse.refreshToken,
            accessTokenExpiredAt = signInResponse.accessTokenExpiredAt,
            refreshTokenExpiredAt = signInResponse.refreshTokenExpiredAt
        )
    }
}