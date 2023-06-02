package com.goms.data.datasource.outing

import com.goms.data.api.OutingApi
import com.goms.data.model.outing.OutingCountResponse
import com.goms.data.model.user.UserResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.util.UUID
import javax.inject.Inject

class OutingDataSourceImpl @Inject constructor(
    private val outingApi: OutingApi
): OutingDataSource {
    override suspend fun outing(outingUUID: UUID): Flow<Response<Unit>> {
        return flow {
            emit(outingApi.outing(outingUUID))
        }
    }

    override suspend fun getOutingList(): Flow<List<UserResponse>> {
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