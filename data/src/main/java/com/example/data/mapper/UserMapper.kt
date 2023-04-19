package com.example.data.mapper

import com.example.data.model.signin.response.SignInResponse
import com.example.domain.data.signin.response.SignInResponseData

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