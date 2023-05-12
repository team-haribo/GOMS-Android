package com.goms.domain.repository

import com.goms.domain.data.council.ModifyRoleRequestData
import com.goms.domain.data.user.UserResponseData
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface CouncilRepository {
    suspend fun getUserList(): Flow<List<UserResponseData>>

    suspend fun modifyRole(body: ModifyRoleRequestData): Flow<Response<Unit>>
}