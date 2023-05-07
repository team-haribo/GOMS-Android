package com.goms.data.repository

import com.goms.data.datasource.outing.OutingDataSource
import com.goms.data.mapper.OutingMapper
import com.goms.data.mapper.ProfileMapper
import com.goms.domain.data.outing.OutingCountResponseData
import com.goms.domain.data.profile.response.ProfileResponseData
import com.goms.domain.repository.OutingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.UUID
import javax.inject.Inject

class OutingRepositoryImpl @Inject constructor(
    private val outingDataSource: OutingDataSource
): OutingRepository {
    override suspend fun outing(outingUUID: UUID) {
        return outingDataSource.outing(outingUUID)
    }

    override suspend fun getOutingList(): Flow<List<ProfileResponseData>> {
        return flow {
            outingDataSource.getOutingList().collect { list ->
                emit(list.map { ProfileMapper.profileResponseToData(it) })
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