package com.goms.domain.repository

import com.goms.domain.data.council.request.ModifyRoleRequestData
import com.goms.domain.data.council.response.MakeQrCodeResponseData
import com.goms.domain.data.council.response.SearchStudentResponseData
import com.goms.domain.data.council.response.UserListResponseData
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import java.util.UUID

interface CouncilRepository {
    suspend fun getUserList(): Flow<List<UserListResponseData>>

    suspend fun modifyRole(body: ModifyRoleRequestData): Flow<Response<Unit>>

    suspend fun setBlackList(accountIdx: UUID): Flow<Response<Unit>>

    suspend fun searchStudent(
        grade: Int,
        classNum: Int,
        name: String,
        isBlackList: Boolean,
        authority: String
    ): Flow<List<SearchStudentResponseData>>

    suspend fun makeQrCode(): Flow<MakeQrCodeResponseData>
}