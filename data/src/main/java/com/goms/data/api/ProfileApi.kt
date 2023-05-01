package com.goms.data.api

import com.goms.data.model.profile.response.ProfileResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface ProfileApi {
    @GET("account/profile/")
    suspend fun getProfile(
        @Header("Authorization") accessToken: String
    ): ProfileResponse
}