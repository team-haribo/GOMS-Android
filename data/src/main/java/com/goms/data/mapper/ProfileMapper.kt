package com.goms.data.mapper

import com.goms.data.mapper.util.StudentInfoMapper
import com.goms.data.model.profile.ProfileResponse
import com.goms.domain.data.profile.ProfileResponseData

object ProfileMapper {
    fun profileResponseToData(profileResponse: ProfileResponse): ProfileResponseData {
        return ProfileResponseData(
            name = profileResponse.name,
            studentNum = StudentInfoMapper.studentInfoToData(profileResponse.studentNum),
            authority = profileResponse.authority,
            profileUrl = profileResponse.profileUrl,
            lateCount = profileResponse.lateCount,
            isOuting = profileResponse.isOuting,
            isBlackList = profileResponse.isBlackList
        )
    }
}