package com.goms.data.model.signin.response

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class SignInResponse(
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("refreshToken") val refreshToken: String,
    @SerializedName("accessTokenExpiredAt") val accessTokenExpiredAt: LocalDateTime?,
    @SerializedName("refreshTokenExpiredAt") val refreshTokenExpiredAt: LocalDateTime?,
    @SerializedName("authority") val authority: String
)
