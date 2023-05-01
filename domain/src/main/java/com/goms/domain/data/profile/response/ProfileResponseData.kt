package com.goms.domain.data.profile.response

import java.util.UUID

data class ProfileResponseData(
    val accountIdx: UUID,
    val name: String,
    val studentNum: StudentInfoData,
    val profileUrl: String?,
    val lateCount: Int
)
