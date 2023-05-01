package com.goms.domain.repository

import com.goms.domain.data.profile.response.ProfileResponseData
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun getProfile(
        accessToken: String
    ): Flow<ProfileResponseData>
}