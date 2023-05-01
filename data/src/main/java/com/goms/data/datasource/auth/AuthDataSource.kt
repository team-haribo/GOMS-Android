package com.goms.data.datasource.auth

import com.goms.data.model.auth.response.RefreshTokenResponse
import com.goms.data.model.auth.response.SignInResponse
import kotlinx.coroutines.flow.Flow

interface AuthDataSource {
    suspend fun signIn(
        code: String
    ): Flow<SignInResponse>

    suspend fun refreshToken(
        refreshToken: String
    ): Flow<RefreshTokenResponse>
}