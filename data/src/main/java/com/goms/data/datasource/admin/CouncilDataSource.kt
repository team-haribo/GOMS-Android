package com.goms.data.datasource.admin

import com.goms.data.model.user.UserResponse
import kotlinx.coroutines.flow.Flow

interface CouncilDataSource {
    suspend fun getUserList(): Flow<List<UserResponse>>
}