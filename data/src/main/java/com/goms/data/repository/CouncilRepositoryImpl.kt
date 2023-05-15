package com.goms.data.repository

import com.goms.data.datasource.admin.CouncilDataSource
import com.goms.data.mapper.CouncilMapper
import com.goms.data.mapper.UserMapper
import com.goms.domain.data.council.request.ModifyRoleRequestData
import com.goms.domain.data.user.UserResponseData
import com.goms.domain.repository.CouncilRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.util.UUID
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

    override suspend fun modifyRole(body: ModifyRoleRequestData): Flow<Response<Unit>> {
        return flow {
            councilDataSource.modifyRole(CouncilMapper.modifyRoleRequestToDomain(body)).collect {
                emit(it)
            }
        }
    }

    override suspend fun setBlackList(accountIdx: UUID): Flow<Response<Unit>> {
        return flow {
            councilDataSource.setBlackList(accountIdx).collect {
                emit(it)
            }
        }
    }
}