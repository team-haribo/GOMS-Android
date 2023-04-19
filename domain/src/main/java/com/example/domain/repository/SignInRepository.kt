package com.example.domain.repository

import com.example.domain.data.signin.response.SignInResponseData
import kotlinx.coroutines.flow.Flow

interface SignInRepository {
    suspend fun signIn(
        code: String
    ): Flow<SignInResponseData>
}