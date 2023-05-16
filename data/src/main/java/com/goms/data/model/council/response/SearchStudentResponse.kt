package com.goms.data.model.council.response

import com.goms.data.model.util.StudentInfo
import com.google.gson.annotations.SerializedName
import java.util.UUID

data class SearchStudentResponse(
    @SerializedName("accountIdx") val accountIdx: UUID,
    @SerializedName("name") val name: String,
    @SerializedName("studentNum") val studentNum: StudentInfo,
    @SerializedName("profileUrl") val profileUrl: String?,
    @SerializedName("authority") val authority: String
)
