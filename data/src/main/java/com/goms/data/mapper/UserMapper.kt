package com.goms.data.mapper

import com.goms.data.model.user.UserInfo
import com.goms.data.model.user.UserResponse
import com.goms.domain.data.user.UserInfoData
import com.goms.domain.data.user.UserResponseData

object UserMapper {
    fun userResponseToData(userResponse: UserResponse): UserResponseData {
        return UserResponseData(
            accountIdx = userResponse.accountIdx,
            name = userResponse.name,
            studentNum = userInfoToData(userResponse.studentNum),
            profileUrl = userResponse.profileUrl,
            lateCount = userResponse.lateCount
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