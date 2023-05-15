package com.goms.domain.repository

import com.goms.domain.data.council.request.ModifyRoleRequestData
import com.goms.domain.data.user.UserResponseData
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import java.util.UUID

interface CouncilRepository {
    suspend fun getUserList(): Flow<List<UserResponseData>>

    suspend fun modifyRole(body: ModifyRoleRequestData): Flow<Response<Unit>>

    suspend fun setBlackList(accountIdx: UUID): Flow<Response<Unit>>
}