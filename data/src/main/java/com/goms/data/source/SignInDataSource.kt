package com.goms.data.source

import com.goms.data.model.signin.response.SignInResponse
import kotlinx.coroutines.flow.Flow

interface SignInDataSource {
    suspend fun signIn(
        code: String
    ): Flow<SignInResponse>
}