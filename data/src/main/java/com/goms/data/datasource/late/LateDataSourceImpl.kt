package com.goms.data.datasource.late

import com.goms.data.api.LateApi
import com.goms.data.model.profile.ProfileResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LateDataSourceImpl @Inject constructor(
    private val lateApi: LateApi
): LateDataSource {
    override suspend fun getLateRank(): Flow<List<ProfileResponse>> {
        return flow {
            emit(lateApi.getLateRank())
        }
    }
}