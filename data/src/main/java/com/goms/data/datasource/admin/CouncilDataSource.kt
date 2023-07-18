package com.goms.data.datasource.admin

import com.goms.data.model.council.request.ModifyRoleRequest
import com.goms.data.model.council.response.MakeQrCodeResponse
import com.goms.data.model.council.response.UserInfoResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import java.util.UUID

interface CouncilDataSource {
    suspend fun getUserList(): Flow<List<UserInfoResponse>>

    suspend fun modifyRole(body: ModifyRoleRequest): Flow<Response<Unit>>

    suspend fun setBlackList(accountIdx: UUID): Flow<Response<Unit>>

    suspend fun cancelBlackList(accountIdx: UUID): Flow<Response<Unit>>

    suspend fun searchStudent(
        grade: Int?,
        classNum: Int?,
        name: String?,
        isBlackList: Boolean?,
        authority: String?
    ): Flow<List<UserInfoResponse>>

    suspend fun makeQrCode(): Flow<MakeQrCodeResponse>

    suspend fun deleteOuting(accountIdx: UUID): Flow<Response<Unit>>
}