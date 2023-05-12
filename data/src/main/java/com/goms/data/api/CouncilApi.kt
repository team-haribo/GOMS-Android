package com.goms.data.api

import com.goms.data.model.council.ModifyRoleRequest
import com.goms.data.model.user.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH

interface CouncilApi {
    @GET("student-council/account")
    suspend fun getUserList(): List<UserResponse>

    @PATCH("student-council/authority")
    suspend fun modifyRole(
        @Body body: ModifyRoleRequest
    ): Response<Unit>
}