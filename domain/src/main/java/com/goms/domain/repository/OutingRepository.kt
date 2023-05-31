package com.goms.domain.repository

import com.goms.domain.data.outing.OutingCountResponseData
import com.goms.domain.data.user.UserResponseData
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import java.util.UUID

interface OutingRepository {
    suspend fun outing(outingUUID: UUID): Flow<Response<Unit>>

    suspend fun getOutingList(): Flow<List<UserResponseData>>

    suspend fun getOutingCount(): Flow<OutingCountResponseData>
}