package com.example.gsm_goms.data.source.signin

import com.example.gsm_goms.data.model.signin.request.SignInRequest
import com.example.gsm_goms.data.model.signin.response.SignInResponse
import kotlinx.coroutines.flow.Flow

interface SignInDataSource {
    suspend fun signIn(
        code: String
    ): Flow<SignInResponse>
}