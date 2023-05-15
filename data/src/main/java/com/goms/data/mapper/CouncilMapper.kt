package com.goms.data.mapper

import com.goms.data.model.council.request.ModifyRoleRequest
import com.goms.data.model.council.response.SearchStudentResponse
import com.goms.data.model.user.UserInfo
import com.goms.domain.data.council.request.ModifyRoleRequestData
import com.goms.domain.data.council.response.SearchStudentResponseData
import com.goms.domain.data.user.UserInfoData

object CouncilMapper {
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
            studentNum = userInfoToData(searchStudentResponse.studentNum),
            profileUrl = searchStudentResponse.profileUrl,
            authority = searchStudentResponse.authority
        )
    }

    private fun userInfoToData(userInfo: UserInfo): UserInfoData {
        return UserInfoData(
            grade = userInfo.grade,
            classNum = userInfo.classNum,
            number = userInfo.number
        )
    }
}