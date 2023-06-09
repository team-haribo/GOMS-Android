package com.goms.data.api

import com.goms.data.model.profile.ProfileResponse
import retrofit2.http.GET

interface ProfileApi {
    @GET("account/profile/")
    suspend fun getProfile(): ProfileResponse
}