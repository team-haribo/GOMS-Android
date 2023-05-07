package com.goms.domain.repository

import com.goms.domain.data.outing.OutingCountResponseData
import com.goms.domain.data.profile.response.ProfileResponseData
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface OutingRepository {
    suspend fun outing(outingUUID: UUID)

    suspend fun getOutingList(): Flow<List<ProfileResponseData>>

    suspend fun getOutingCount(): Flow<OutingCountResponseData>
}