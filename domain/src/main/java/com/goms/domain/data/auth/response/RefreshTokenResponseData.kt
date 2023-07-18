package com.goms.domain.data.auth.response

data class RefreshTokenResponseData(
    val accessToken: String,
    val refreshToken: String,
    val accessTokenExpiredAt: String?,
    val refreshTokenExpiredAt: String?,
    val authority: String
)
