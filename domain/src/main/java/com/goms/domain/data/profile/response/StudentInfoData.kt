package com.goms.domain.data.profile.response

import java.io.Serializable

data class StudentInfoData(
    val grade: Int,
    val classNum: Int,
    val number: Int
): Serializable