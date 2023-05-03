package com.goms.data.datasource.outing

import com.goms.data.api.OutingApi
import com.goms.data.model.outing.OutingCountResponse
import com.goms.data.model.profile.ProfileResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OutingDataSourceImpl @Inject constructor(
    private val outingApi: OutingApi
): OutingDataSource {
    override suspend fun outing() {
        return outingApi.outing()
    }

    override suspend fun getOutingList(): Flow<List<ProfileResponse>> {
        return flow {
            emit(outingApi.getOutingList())
        }
    }

    override suspend fun getOutingCount(): Flow<OutingCountResponse> {
        return flow {
            emit(outingApi.getOutingCount())
        }
    }
}