package com.goms.data.mapper

import com.goms.data.mapper.util.StudentInfoMapper
import com.goms.data.model.council.request.ModifyRoleRequest
import com.goms.data.model.council.response.SearchStudentResponse
import com.goms.data.model.council.response.UserListResponse
import com.goms.domain.data.council.request.ModifyRoleRequestData
import com.goms.domain.data.council.response.SearchStudentResponseData
import com.goms.domain.data.council.response.UserListResponseData

object CouncilMapper {
    fun userListResponseToData(userListResponse: UserListResponse): UserListResponseData {
        return UserListResponseData(
            accountIdx = userListResponse.accountIdx,
            name = userListResponse.name,
            studentNum = StudentInfoMapper.studentInfoToData(userListResponse.studentNum),
            profileUrl = userListResponse.profileUrl,
            authority = userListResponse.authority,
            isBlackList = userListResponse.isBlackList
        )
    }

    fun modifyRoleRequestToDomain(modifyRoleRequestData: ModifyRoleRequestData): ModifyRoleRequest {
        return ModifyRoleRequest(
            accountIdx = modifyRoleRequestData.accountIdx,
            authority = modifyRoleRequestData.authority
        )
    }

    fun searchStudentToData(searchStudentResponse: SearchStudentResponse): SearchStudentResponseData {
        return SearchStudentResponseData(
            accountIdx = searchStudentResponse.accountIdx,
            name = searchStudentResponse.name,
            studentNum = StudentInfoMapper.studentInfoToData(searchStudentResponse.studentNum),
            profileUrl = searchStudentResponse.profileUrl,
            authority = searchStudentResponse.authority
        )
    }
}