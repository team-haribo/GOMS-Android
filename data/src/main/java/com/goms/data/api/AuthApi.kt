package com.goms.data.api

import com.goms.data.model.auth.request.SignInRequest
import com.goms.data.model.auth.response.RefreshTokenResponse
import com.goms.data.model.auth.response.SignInResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/signin")
    suspend fun signIn(
        @Body body: SignInRequest
    ): SignInResponse

    @PATCH("auth")
    suspend fun refreshToken(
        @Header("refreshToken") refreshToken: String
    ): RefreshTokenResponse
}