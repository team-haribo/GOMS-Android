package com.goms.data.api

import com.goms.data.model.outing.OutingCountResponse
import com.goms.data.model.user.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.UUID

interface OutingApi {
    @POST("outing/{outingUUID}")
    suspend fun outing(@Path("outingUUID") outingUUID: UUID): Response<Unit>

    @GET("outing")
    suspend fun getOutingList(): List<UserResponse>

    @GET("outing/count")
    suspend fun getOutingCount(): OutingCountResponse
}