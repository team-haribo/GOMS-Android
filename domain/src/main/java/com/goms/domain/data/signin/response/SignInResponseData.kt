package com.goms.domain.data.signin.response

data class SignInResponseData(
    val accessToken: String,
    val refreshToken: String,
    val accessTokenExp: String?,
    val refreshTokenExp: String?,
    val authority: String
)
