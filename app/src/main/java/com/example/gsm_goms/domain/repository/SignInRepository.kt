package com.example.gsm_goms.domain.repository

import com.example.gsm_goms.domain.data.signin.request.SignInRequestData
import com.example.gsm_goms.domain.data.signin.response.SignInResponseData
import kotlinx.coroutines.flow.Flow

interface SignInRepository {
    suspend fun signIn(
        code: String
    ): Flow<SignInResponseData>
}