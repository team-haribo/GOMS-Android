package com.goms.data.datasource.admin

import com.goms.data.model.council.ModifyRoleRequest
import com.goms.data.model.user.UserResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface CouncilDataSource {
    suspend fun getUserList(): Flow<List<UserResponse>>

    suspend fun modifyRole(body: ModifyRoleRequest): Flow<Response<Unit>>
}