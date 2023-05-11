package com.goms.data.api

import com.goms.data.model.user.UserResponse
import retrofit2.http.GET

interface CouncilApi {
    @GET("student-council/account")
    suspend fun getUserList(): List<UserResponse>
}