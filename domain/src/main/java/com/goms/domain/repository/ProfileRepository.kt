package com.goms.domain.repository

import com.goms.domain.data.profile.ProfileResponseData
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun getProfile(): Flow<ProfileResponseData>
}