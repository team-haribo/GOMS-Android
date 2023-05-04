package com.goms.domain.repository

import com.goms.domain.data.profile.response.ProfileResponseData
import kotlinx.coroutines.flow.Flow

interface LateRepository {
    suspend fun getLateRank(): Flow<List<ProfileResponseData>>
}