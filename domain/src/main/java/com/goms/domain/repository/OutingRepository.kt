package com.goms.domain.repository

import com.goms.domain.data.outing.OutingCountResponseData
import com.goms.domain.data.profile.response.ProfileResponseData
import kotlinx.coroutines.flow.Flow

interface OutingRepository {
    suspend fun outing()

    suspend fun getOutingList(): Flow<List<ProfileResponseData>>

    suspend fun getOutingCount(): Flow<OutingCountResponseData>
}