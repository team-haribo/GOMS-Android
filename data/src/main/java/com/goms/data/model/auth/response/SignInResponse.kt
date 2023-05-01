package com.goms.data.model.auth.response

import com.google.gson.annotations.SerializedName

data class SignInResponse(
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("refreshToken") val refreshToken: String,
    @SerializedName("accessTokenExp") val accessTokenExpiredAt: String?,
    @SerializedName("refreshTokenExp") val refreshTokenExpiredAt: String?,
    @SerializedName("authority") val authority: String
)
