package com.goms.data.datasource.profile

import com.goms.data.api.ProfileApi
import com.goms.data.model.profile.response.ProfileResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProfileDataSourceImpl @Inject constructor(
    private val profileApi: ProfileApi
): ProfileDataSource {
    override suspend fun getProfile(): Flow<ProfileResponse> {
        return flow {
            emit(profileApi.getProfile())
        }
    }
}