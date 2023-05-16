package com.goms.data.mapper.util

import com.goms.data.model.util.StudentInfo
import com.goms.domain.data.util.StudentInfoData

object StudentInfoMapper {
    fun studentInfoToData(studentInfo: StudentInfo): StudentInfoData {
        return StudentInfoData(
            grade = studentInfo.grade,
            classNum = studentInfo.classNum,
            number = studentInfo.number
        )
    }
}