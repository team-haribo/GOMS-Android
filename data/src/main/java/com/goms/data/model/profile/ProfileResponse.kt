package com.goms.data.model.profile

import com.goms.data.model.util.StudentInfo
import com.google.gson.annotations.SerializedName

data class ProfileResponse(
    @SerializedName("name") val name: String,
    @SerializedName("studentNum") val studentNum: StudentInfo,
    @SerializedName("authority") val authority: String,
    @SerializedName("profileUrl") val profileUrl: String?,
    @SerializedName("lateCount") val lateCount: Int,
    @SerializedName("isOuting") val isOuting: Boolean,
    @SerializedName("isBlackList") val isBlackList: Boolean
)
