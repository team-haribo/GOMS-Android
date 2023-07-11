package com.goms.domain.data.profile

import com.goms.domain.data.util.StudentInfoData
import java.io.Serializable
import java.util.UUID

data class ProfileResponseData(
    val accountIdx: UUID,
    val name: String,
    val studentNum: StudentInfoData,
    val authority: String,
    val profileUrl: String?,
    val lateCount: Int,
    val isOuting: Boolean,
    val isBlackList: Boolean
): Serializable
