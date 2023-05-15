package com.goms.domain.data.council.response

import com.goms.domain.data.user.UserInfoData
import java.util.UUID

data class SearchStudentResponseData(
    val accountIdx: UUID,
    val name: String,
    val studentNum: UserInfoData,
    val profileUrl: String?,
    val authority: String
)
