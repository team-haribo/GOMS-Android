package com.goms.data.datasource.token

interface AuthTokenDataSource {
    fun getAccessToken(): String
    fun getRefreshToken(): String
    fun getAccessTokenExp(): String
    fun getRefreshTokenExp(): String

    fun setToken(
        accessToken: String,
        refreshToken: String,
        accessTokenExp: String,
        refreshTokenExp: String
    )
}