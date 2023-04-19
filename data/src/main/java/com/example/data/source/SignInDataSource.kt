package com.example.data.source

import com.example.data.model.signin.response.SignInResponse
import kotlinx.coroutines.flow.Flow

interface SignInDataSource {
    suspend fun signIn(
        code: String
    ): Flow<SignInResponse>
}