package com.goms.data.api

import com.goms.data.model.council.request.ModifyRoleRequest
import com.goms.data.model.council.response.SearchStudentResponse
import com.goms.data.model.user.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.UUID

interface CouncilApi {
    @GET("student-council/account")
    suspend fun getUserList(): List<UserResponse>

    @PATCH("student-council/authority")
    suspend fun modifyRole(
        @Body body: ModifyRoleRequest
    ): Response<Unit>

    @POST("student-council/black-list/{accountIdx}")
    suspend fun setBlackList(
        @Path("accountIdx") accountIdx: UUID
    ): Response<Unit>

    @GET("student-council/search")
    suspend fun searchStudent(
        @Query("grade") grade: Int,
        @Query("classNum") classNum: Int,
        @Query("number") number: Int,
        @Query("name") name: String,
        @Query("isBlackList") isBlackList: Boolean,
        @Query("authority") authority: String
    ): List<SearchStudentResponse>
}