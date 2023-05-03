package com.goms.domain.repository

import com.goms.domain.data.outing.OutingCountResponseData
import com.goms.domain.data.profile.response.ProfileResponseData
import kotlinx.coroutines.flow.Flow

interface OutingRepository {
    suspend fun outing()

    suspend fun outingList(): Flow<List<ProfileResponseData>>

    suspend fun outingCount(): Flow<OutingCountResponseData>
}