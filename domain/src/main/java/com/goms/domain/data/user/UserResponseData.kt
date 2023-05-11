package com.goms.domain.data.user

import java.io.Serializable
import java.util.UUID

data class UserResponseData(
    val accountIdx: UUID,
    val name: String,
    val studentNum: UserInfoData,
    val profileUrl: String?,
    val lateCount: Int
): Serializable
