package com.goms.data.datasource.auth

import com.goms.data.api.AuthApi
import com.goms.data.model.auth.request.SignInRequest
import com.goms.data.model.auth.response.RefreshTokenResponse
import com.goms.data.model.auth.response.SignInResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val authApi: AuthApi
): AuthDataSource {
    override suspend fun signIn(code: String): Flow<SignInResponse> {
        return flow {
            emit(authApi.signIn(SignInRequest(code)))
        }
    }

    override suspend fun refreshToken(refreshToken: String): Flow<RefreshTokenResponse> {
        return flow {
            emit(authApi.refreshToken(refreshToken))
        }
    }
}