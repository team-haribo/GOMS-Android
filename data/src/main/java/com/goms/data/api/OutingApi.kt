package com.goms.data.api

import com.goms.data.model.outing.OutingCountResponse
import com.goms.data.model.profile.ProfileResponse
import retrofit2.http.GET
import retrofit2.http.POST

interface OutingApi {
    @POST("outing")
    suspend fun outing()

    @GET("outing")
    suspend fun outingList(): List<ProfileResponse>

    @GET("outing/count")
    suspend fun outingCount(): OutingCountResponse
}