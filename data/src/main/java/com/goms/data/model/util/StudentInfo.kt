package com.goms.data.model.util

import com.google.gson.annotations.SerializedName

data class StudentInfo(
    @SerializedName("grade") val grade: Int,
    @SerializedName("classNum") val classNum: Int,
    @SerializedName("number") val number: Int
)
