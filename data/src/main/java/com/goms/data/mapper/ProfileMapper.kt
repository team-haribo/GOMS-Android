package com.goms.data.mapper

import com.goms.data.mapper.util.StudentInfoMapper
import com.goms.data.model.profile.ProfileResponse
import com.goms.domain.data.profile.ProfileResponseData

object ProfileMapper {
    fun profileResponseToData(profileResponse: ProfileResponse): ProfileResponseData {
        return ProfileResponseData(
            accountIdx = profileResponse.accountIdx,
            name = profileResponse.name,
            studentNum = StudentInfoMapper.studentInfoToData(profileResponse.studentNum),
            profileUrl = profileResponse.profileUrl,
            lateCount = profileResponse.lateCount
        )
    }
}