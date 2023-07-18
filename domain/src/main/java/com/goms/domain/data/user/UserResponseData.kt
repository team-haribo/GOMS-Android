package com.goms.domain.data.user

import com.goms.domain.data.util.StudentInfoData
import java.io.Serializable
import java.util.UUID

data class UserResponseData(
    val accountIdx: UUID,
    val name: String,
    val studentNum: StudentInfoData,
    val profileUrl: String?,
    val createdTime: String
): Serializable
