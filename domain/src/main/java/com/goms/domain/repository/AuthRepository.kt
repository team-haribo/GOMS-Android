package com.goms.domain.repository

import com.goms.domain.data.signin.response.SignInResponseData
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signIn(
        code: String
    ): Flow<SignInResponseData>

    suspend fun checkLoginStatus()

    fun getAccessToken(): String
    fun getRefreshToken(): String
    fun getAccessTokenExp(): String
    fun getRefreshTokenExp(): String

    suspend fun setToken(
        accessToken: String,
        refreshToken: String,
        accessTokenExp: String,
        refreshTokenExp: String
    )
}