package com.goms.domain.repository

import com.goms.domain.data.signin.response.SignInResponseData
import kotlinx.coroutines.flow.Flow

interface SignInRepository {
    suspend fun signIn(
        code: String
    ): Flow<SignInResponseData>
}