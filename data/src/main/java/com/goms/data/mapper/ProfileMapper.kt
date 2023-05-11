package com.goms.data.mapper

import com.goms.data.model.profile.ProfileResponse
import com.goms.data.model.profile.StudentInfo
import com.goms.domain.data.profile.ProfileResponseData
import com.goms.domain.data.profile.StudentInfoData

object ProfileMapper {
    fun profileResponseToData(profileResponse: ProfileResponse): ProfileResponseData {
        return ProfileResponseData(
            accountIdx = profileResponse.accountIdx,
            name = profileResponse.name,
            studentNum = studentInfoToData(profileResponse.studentNum),
            profileUrl = profileResponse.profileUrl,
            lateCount = profileResponse.lateCount
        )
    }

    private fun studentInfoToData(studentInfo: StudentInfo): StudentInfoData {
        return StudentInfoData(
            grade = studentInfo.grade,
            classNum = studentInfo.classNum,
            number = studentInfo.number
        )
    }
}