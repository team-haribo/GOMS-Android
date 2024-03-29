package com.goms.data.api.interceptor

import com.goms.data.BuildConfig
import com.goms.data.datasource.token.AuthTokenDataSource
import com.goms.domain.exception.NeedLoginException
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import okhttp3.Interceptor
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class LoginInterceptor @Inject constructor(
    private val authTokenDataSource: AuthTokenDataSource
): Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        if ("/api/v1/auth" == request.url.encodedPath && "PATCH" == request.method)
            return chain.proceed(request)
        if ("/api/v1/auth/signin" == request.url.encodedPath && "POST" == request.method)
            return chain.proceed(request)

        val currentTime = LocalDateTime.now()
        val parsePattern = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        val accessTokenExp = authTokenDataSource.getAccessTokenExp()
        val refreshTokenExp = authTokenDataSource.getRefreshTokenExp()
        val accessTokenExpireDate = LocalDateTime.parse(accessTokenExp, parsePattern)
        val refreshTokenExpireDate = LocalDateTime.parse(refreshTokenExp, parsePattern)

        if (currentTime.isAfter(refreshTokenExpireDate)) throw NeedLoginException()
        if (currentTime.isAfter(accessTokenExpireDate)) {
            tokenRefresh(chain)
        }

        // 이 부분이 기존 요청에 token header를 붙여 return 하는 곳
        val accessToken = authTokenDataSource.getAccessToken()
        return chain.proceed(
            request.newBuilder()
                .addHeader("Authorization", "Bearer $accessToken")
                .build()
        )
    }

    private fun tokenRefresh(chain: Interceptor.Chain) {
        val currentRefreshToken = authTokenDataSource.getRefreshToken()
        val refreshRequest = chain.request().newBuilder()
            .url(BuildConfig.BASE_URL+"auth")
            .addHeader("refreshToken", "Bearer $currentRefreshToken")
            .patch("".toRequestBody(null))
            .build()
        val refreshResponse = chain.proceed(refreshRequest)
        if (refreshResponse.isSuccessful) {
            val jsonParser = JsonParser()
            val token = jsonParser.parse(refreshResponse.body?.string()) as JsonObject
            authTokenDataSource.setToken(
                accessToken = token["accessToken"].toString().deleteDot(),
                refreshToken = token["refreshToken"].toString().deleteDot(),
                accessTokenExp = token["accessTokenExp"].toString().deleteDot(),
                refreshTokenExp = token["refreshTokenExp"].toString().deleteDot()
            )
        } else throw NeedLoginException()
    }

    private fun String.deleteDot(): String {
        return this.substring(1, this.length-1)
    }
}