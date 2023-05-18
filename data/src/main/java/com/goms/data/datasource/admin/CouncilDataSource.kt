package com.goms.data.datasource.admin

import com.goms.data.model.council.request.ModifyRoleRequest
import com.goms.data.model.council.response.MakeQrCodeResponse
import com.goms.data.model.council.response.SearchStudentResponse
import com.goms.data.model.council.response.UserListResponse
import com.goms.domain.data.council.response.UserListResponseData
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import java.util.UUID

interface CouncilDataSource {
    suspend fun getUserList(): Flow<List<UserListResponse>>

    suspend fun modifyRole(body: ModifyRoleRequest): Flow<Response<Unit>>

    suspend fun setBlackList(accountIdx: UUID): Flow<Response<Unit>>

    suspend fun searchStudent(
        grade: Int?,
        classNum: Int?,
        name: String?,
        isBlackList: Boolean?,
        authority: String?
    ): Flow<List<UserListResponse>>

    suspend fun makeQrCode(): Flow<MakeQrCodeResponse>
}