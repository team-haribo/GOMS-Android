package com.goms.data.repository

import com.goms.data.datasource.admin.CouncilDataSource
import com.goms.data.mapper.UserMapper
import com.goms.domain.data.user.UserResponseData
import com.goms.domain.repository.CouncilRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CouncilRepositoryImpl @Inject constructor(
    private val councilDataSource: CouncilDataSource
): CouncilRepository {
    override suspend fun getUserList(): Flow<List<UserResponseData>> {
        return flow {
            councilDataSource.getUserList().collect { list ->
                emit(list.map { UserMapper.userResponseToData(it) })
            }
        }
    }
}