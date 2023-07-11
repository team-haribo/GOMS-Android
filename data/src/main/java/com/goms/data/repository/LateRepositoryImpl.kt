package com.goms.data.repository

import com.goms.data.datasource.late.LateDataSource
import com.goms.data.mapper.LateMapper
import com.goms.domain.data.late.LateUserResponseData
import com.goms.domain.repository.LateRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LateRepositoryImpl @Inject constructor(
    private val lateDataSource: LateDataSource
): LateRepository {
    override suspend fun getLateRank(): Flow<List<LateUserResponseData>> {
        return flow {
            lateDataSource.getLateRank().collect { list ->
                emit(list.map { LateMapper.lateUserResponseToData(it) })
            }
        }
    }
}