package com.goms.data.datasource.outing

import com.goms.data.model.outing.OutingCountResponse
import com.goms.data.model.profile.ProfileResponse
import kotlinx.coroutines.flow.Flow

interface OutingDataSource {
    suspend fun outing()

    suspend fun getOutingList(): Flow<List<ProfileResponse>>

    suspend fun getOutingCount(): Flow<OutingCountResponse>
}