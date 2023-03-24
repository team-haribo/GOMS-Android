package com.example.gsm_goms.data.api

import com.example.gsm_goms.data.model.signin.request.SignInRequest
import com.example.gsm_goms.data.model.signin.response.SignInResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface SignInService {
    @POST("/student/signin")
    suspend fun signIn(
        @Body body: SignInRequest
    ): SignInResponse
}