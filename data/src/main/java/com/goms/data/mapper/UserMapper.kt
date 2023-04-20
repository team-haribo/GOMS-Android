package com.goms.data.mapper

import com.goms.data.model.signin.response.SignInResponse
import com.goms.domain.data.signin.response.SignInResponseData

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