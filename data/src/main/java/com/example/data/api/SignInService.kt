package com.example.data.api

import com.example.data.model.signin.request.SignInRequest
import com.example.data.model.signin.response.SignInResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface SignInService {
    @POST("/api/v1/auth/signin")
    suspend fun signIn(
        @Body body: SignInRequest
    ): SignInResponse
}