package com.goms.data.repository

import com.goms.data.datasource.late.LateDataSource
import com.goms.data.mapper.ProfileMapper
import com.goms.domain.data.profile.ProfileResponseData
import com.goms.domain.repository.LateRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LateRepositoryImpl @Inject constructor(
    private val lateDataSource: LateDataSource
): LateRepository {
    override suspend fun getLateRank(): Flow<List<ProfileResponseData>> {
        return flow {
            lateDataSource.getLateRank().collect { list ->
                emit(list.map { ProfileMapper.profileResponseToData(it) })
            }
        }
    }
}