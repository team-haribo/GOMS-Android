package com.goms.data.mapper

import com.goms.data.model.auth.response.SignInResponse
import com.goms.domain.data.auth.response.SignInResponseData

object AuthMapper {
    fun signInResponseToData(signInResponse: SignInResponse): SignInResponseData {
        return SignInResponseData(
            accessToken = signInResponse.accessToken,
            refreshToken = signInResponse.refreshToken,
            accessTokenExp = signInResponse.accessTokenExpiredAt,
            refreshTokenExp = signInResponse.refreshTokenExpiredAt,
            authority = signInResponse.authority
        )
    }
}