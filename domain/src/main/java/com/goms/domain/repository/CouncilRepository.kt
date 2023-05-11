package com.goms.domain.repository

import com.goms.domain.data.user.UserResponseData
import kotlinx.coroutines.flow.Flow

interface CouncilRepository {
    suspend fun getUserList(): Flow<List<UserResponseData>>
}