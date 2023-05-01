package com.goms.data.repository

import com.goms.data.datasource.profile.ProfileDataSource
import com.goms.data.mapper.ProfileMapper
import com.goms.domain.data.profile.response.ProfileResponseData
import com.goms.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val profileDataSource: ProfileDataSource
): ProfileRepository {
    override suspend fun getProfile(accessToken: String): Flow<ProfileResponseData> {
        return flow {
            profileDataSource.getProfile(accessToken).collect {
                emit(ProfileMapper.profileResponseToData(it))
            }
        }
    }
}