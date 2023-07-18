package com.goms.data.datasource.late

import com.goms.data.model.late.LateUserResponse
import kotlinx.coroutines.flow.Flow

interface LateDataSource {
    suspend fun getLateRank(): Flow<List<LateUserResponse>>
}