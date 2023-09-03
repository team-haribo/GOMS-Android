package com.goms.data.api

import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Path

interface NotificationApi {
    @POST("notification/token/{deviceToken}")
    suspend fun setNotification(
        @Path("deviceToken") deviceToken: String
    ): Response<Unit>
}