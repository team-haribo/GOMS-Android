package com.goms.domain.repository

import com.goms.domain.data.profile.ProfileResponseData
import kotlinx.coroutines.flow.Flow

interface LateRepository {
    suspend fun getLateRank(): Flow<List<ProfileResponseData>>
}