package com.goms.domain.data.profile

import java.io.Serializable
import java.util.UUID

data class ProfileResponseData(
    val accountIdx: UUID,
    val name: String,
    val studentNum: StudentInfoData,
    val profileUrl: String?,
    val lateCount: Int
): Serializable
