package com.goms.data.mapper

import com.goms.data.mapper.util.StudentInfoMapper
import com.goms.data.model.council.request.ModifyRoleRequest
import com.goms.data.model.council.response.UserInfoResponse
import com.goms.domain.data.council.request.ModifyRoleRequestData
import com.goms.domain.data.council.response.UserInfoResponseData

object CouncilMapper {
    fun userListResponseToData(userInfoResponse: UserInfoResponse): UserInfoResponseData {
        return UserInfoResponseData(
            accountIdx = userInfoResponse.accountIdx,
            name = userInfoResponse.name,
            studentNum = StudentInfoMapper.studentInfoToData(userInfoResponse.studentNum),
            profileUrl = userInfoResponse.profileUrl,
            authority = userInfoResponse.authority,
            isBlackList = userInfoResponse.isBlackList
        )
    }

    fun modifyRoleRequestToDomain(modifyRoleRequestData: ModifyRoleRequestData): ModifyRoleRequest {
        return ModifyRoleRequest(
            accountIdx = modifyRoleRequestData.accountIdx,
            authority = modifyRoleRequestData.authority
        )
    }
}