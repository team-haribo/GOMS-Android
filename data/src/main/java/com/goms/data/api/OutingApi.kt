package com.goms.data.api

import com.goms.data.model.outing.OutingCountResponse
import com.goms.data.model.profile.ProfileResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.UUID

interface OutingApi {
    @POST("outing/{outingUUID}")
    suspend fun outing(@Path("outingUUID") outingUUID: UUID)

    @GET("outing")
    suspend fun getOutingList(): List<ProfileResponse>

    @GET("outing/count")
    suspend fun getOutingCount(): OutingCountResponse
}