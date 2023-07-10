package com.goms.data.model.user

import com.goms.data.model.util.StudentInfo
import com.google.gson.annotations.SerializedName
import java.util.UUID

data class UserResponse(
    @SerializedName("accountIdx") val accountIdx: UUID,
    @SerializedName("name") val name: String,
    @SerializedName("studentNum") val studentNum: StudentInfo,
    @SerializedName("profileUrl") val profileUrl: String?,
    @SerializedName("lateCount") val lateCount: Int,
    @SerializedName("createdTime") val createdTime: String
)
