package com.goms.data.model.user

import com.google.gson.annotations.SerializedName

data class UserInfo(
    @SerializedName("grade") val grade: Int,
    @SerializedName("classNum") val classNum: Int,
    @SerializedName("number") val number: Int
)
