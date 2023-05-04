package com.goms.data.datasource.late

import com.goms.data.model.profile.ProfileResponse
import kotlinx.coroutines.flow.Flow

interface LateDataSource {
    suspend fun getLateRank(): Flow<List<ProfileResponse>>
}