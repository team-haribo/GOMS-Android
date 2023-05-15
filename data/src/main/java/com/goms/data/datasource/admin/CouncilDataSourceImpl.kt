package com.goms.data.datasource.admin

import com.goms.data.api.CouncilApi
import com.goms.data.model.council.request.ModifyRoleRequest
import com.goms.data.model.council.response.MakeQrCodeResponse
import com.goms.data.model.council.response.SearchStudentResponse
import com.goms.data.model.user.UserResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.util.UUID
import javax.inject.Inject

class CouncilDataSourceImpl @Inject constructor(
    private val councilApi: CouncilApi
): CouncilDataSource {
    override suspend fun getUserList(): Flow<List<UserResponse>> {
        return flow {
            emit(councilApi.getUserList())
        }
    }

    override suspend fun modifyRole(body: ModifyRoleRequest): Flow<Response<Unit>> {
        return flow {
            emit(councilApi.modifyRole(body))
        }
    }

    override suspend fun setBlackList(accountIdx: UUID): Flow<Response<Unit>> {
        return flow {
            emit(councilApi.setBlackList(accountIdx))
        }
    }

    override suspend fun searchStudent(
        grade: Int,
        classNum: Int,
        name: String,
        isBlackList: Boolean,
        authority: String
    ): Flow<List<SearchStudentResponse>> {
        return flow {
            emit(councilApi.searchStudent(grade, classNum, name, isBlackList, authority))
        }
    }

    override suspend fun makeQrCode(): Flow<MakeQrCodeResponse> {
        return flow {
            emit(councilApi.makeQrCode())
        }
    }
}