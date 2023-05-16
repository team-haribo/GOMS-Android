package com.goms.data.mapper

import com.goms.data.mapper.util.StudentInfoMapper
import com.goms.data.model.user.UserResponse
import com.goms.domain.data.user.UserResponseData

object UserMapper {
    fun userResponseToData(userResponse: UserResponse): UserResponseData {
        return UserResponseData(
            accountIdx = userResponse.accountIdx,
            name = userResponse.name,
            studentNum = StudentInfoMapper.studentInfoToData(userResponse.studentNum),
            profileUrl = userResponse.profileUrl,
            lateCount = userResponse.lateCount
        )
    }
}