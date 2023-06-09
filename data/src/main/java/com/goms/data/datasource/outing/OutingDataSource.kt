package com.goms.data.datasource.outing

import com.goms.data.model.outing.OutingCountResponse
import com.goms.data.model.user.UserResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import java.util.UUID

interface OutingDataSource {
    suspend fun outing(outingUUID: UUID): Flow<Response<Unit>>

    suspend fun getOutingList(): Flow<List<UserResponse>>

    suspend fun getOutingCount(): Flow<OutingCountResponse>

    suspend fun searchOutingStudent(name: String): Flow<List<UserResponse>>
}