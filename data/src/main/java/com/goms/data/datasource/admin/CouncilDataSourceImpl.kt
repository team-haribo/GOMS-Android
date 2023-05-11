package com.goms.data.datasource.admin

import com.goms.data.api.CouncilApi
import com.goms.data.model.user.UserResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CouncilDataSourceImpl @Inject constructor(
    private val councilApi: CouncilApi
): CouncilDataSource {
    override suspend fun getUserList(): Flow<List<UserResponse>> {
        return flow {
            emit(councilApi.getUserList())
        }
    }
}