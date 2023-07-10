package com.goms.domain.data.late

import com.goms.domain.data.util.StudentInfoData
import java.util.UUID

data class LateUserResponseData(
    val accountIdx: UUID,
    val name: String,
    val studentNum: StudentInfoData,
    val profileUrl: String?
)
