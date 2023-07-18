package com.goms.data.model.late

import com.goms.data.model.util.StudentInfo
import com.google.gson.annotations.SerializedName
import java.util.UUID

data class LateUserResponse(
    @SerializedName("accountIdx") val accountIdx: UUID,
    @SerializedName("name") val name: String,
    @SerializedName("studentNum") val studentNum: StudentInfo,
    @SerializedName("profileUrl") val profileUrl: String?
)
