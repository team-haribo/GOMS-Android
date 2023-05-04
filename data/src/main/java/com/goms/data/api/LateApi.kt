package com.goms.data.api

import com.goms.data.model.profile.ProfileResponse
import retrofit2.http.GET

interface LateApi {
    @GET("late/rank")
    suspend fun getLateRank(): List<ProfileResponse>
}