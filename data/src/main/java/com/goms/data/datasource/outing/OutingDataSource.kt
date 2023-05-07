package com.goms.data.datasource.outing

import com.goms.data.model.outing.OutingCountResponse
import com.goms.data.model.profile.ProfileResponse
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface OutingDataSource {
    suspend fun outing(outingUUID: UUID)

    suspend fun getOutingList(): Flow<List<ProfileResponse>>

    suspend fun getOutingCount(): Flow<OutingCountResponse>
}