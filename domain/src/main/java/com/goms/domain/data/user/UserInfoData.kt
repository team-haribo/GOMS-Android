package com.goms.domain.data.user

import java.io.Serializable

data class UserInfoData(
    val grade: Int,
    val classNum: Int,
    val number: Int
): Serializable
