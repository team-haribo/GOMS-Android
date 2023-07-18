package com.goms.domain.repository

import com.goms.domain.data.late.LateUserResponseData
import kotlinx.coroutines.flow.Flow

interface LateRepository {
    suspend fun getLateRank(): Flow<List<LateUserResponseData>>
}