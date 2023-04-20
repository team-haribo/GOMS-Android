package com.goms.domain.data.signin.response

import java.time.LocalDateTime

data class SignInResponseData(
    val accessToken: String,
    val refreshToken: String,
    val accessTokenExpiredAt: LocalDateTime,
    val refreshTokenExpiredAt: LocalDateTime
)
