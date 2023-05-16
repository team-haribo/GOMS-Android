package com.goms.domain.data.council.response

import com.goms.domain.data.util.StudentInfoData
import java.util.UUID

data class SearchStudentResponseData(
    val accountIdx: UUID,
    val name: String,
    val studentNum: StudentInfoData,
    val profileUrl: String?,
    val authority: String
)
