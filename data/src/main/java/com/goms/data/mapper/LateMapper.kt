package com.goms.data.mapper

import com.goms.data.mapper.util.StudentInfoMapper
import com.goms.data.model.late.LateUserResponse
import com.goms.domain.data.late.LateUserResponseData

object LateMapper {
    fun lateUserResponseToData(lateUserResponse: LateUserResponse): LateUserResponseData {
        return LateUserResponseData(
            accountIdx = lateUserResponse.accountIdx,
            name = lateUserResponse.name,
            studentNum = StudentInfoMapper.studentInfoToData(lateUserResponse.studentNum),
            profileUrl = lateUserResponse.profileUrl
        )
    }
}