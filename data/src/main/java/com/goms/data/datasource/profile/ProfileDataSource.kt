package com.goms.data.datasource.profile

import com.goms.data.model.profile.response.ProfileResponse
import kotlinx.coroutines.flow.Flow

interface ProfileDataSource {
    suspend fun getProfile(): Flow<ProfileResponse>
}