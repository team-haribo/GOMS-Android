package com.goms.data.repository

import com.goms.data.datasource.outing.OutingDataSource
import com.goms.data.mapper.OutingMapper
import com.goms.data.mapper.UserMapper
import com.goms.domain.data.outing.OutingCountResponseData
import com.goms.domain.data.user.UserResponseData
import com.goms.domain.repository.OutingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.util.UUID
import javax.inject.Inject

class OutingRepositoryImpl @Inject constructor(
    private val outingDataSource: OutingDataSource
): OutingRepository {
    override suspend fun outing(outingUUID: UUID): Flow<Response<Unit>> {
        return flow {
            outingDataSource.outing(outingUUID).collect {
                emit(it)
            }
        }
    }

    override suspend fun getOutingList(): Flow<List<UserResponseData>> {
        return flow {
            outingDataSource.getOutingList().collect { list ->
                emit(list.map { UserMapper.userResponseToData(it) })
            }
        }
    }

    override suspend fun getOutingCount(): Flow<OutingCountResponseData> {
        return flow {
            outingDataSource.getOutingCount().collect {
                emit(OutingMapper.outingCountResponseToData(it))
            }
        }
    }
}