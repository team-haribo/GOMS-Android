package com.goms.data.mapper

import com.goms.data.model.auth.response.RefreshTokenResponse
import com.goms.data.model.auth.response.SignInResponse
import com.goms.domain.data.auth.response.RefreshTokenResponseData
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

    fun refreshTokenResponseToData(refreshTokenResponse: RefreshTokenResponse): RefreshTokenResponseData {
        return RefreshTokenResponseData(
            accessToken = refreshTokenResponse.accessToken,
            refreshToken = refreshTokenResponse.refreshToken,
            accessTokenExpiredAt = refreshTokenResponse.accessTokenExpiredAt,
            refreshTokenExpiredAt = refreshTokenResponse.refreshTokenExpiredAt,
            authority = refreshTokenResponse.authority
        )
    }
}