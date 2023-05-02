package com.goms.data.api.interceptor

import com.example.data.BuildConfig
import com.goms.data.datasource.token.AuthTokenDataSource
import com.goms.domain.exception.NeedLoginException
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import okhttp3.Interceptor
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class LoginInterceptor @Inject constructor(
    private val authTokenDataSource: AuthTokenDataSource
): Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        // 토큰 갱신 요청인지, 로그인 요청인지 확인해 따로 리턴한다.
        val ignorePath = listOf("/api/v1/auth", "/api/v1/auth/signin")
        val ignoreMethod = listOf("PATCH", "POST")
        if (ignorePath[0] == request.url.encodedPath && ignoreMethod[0] == request.method) {
            return chain.proceed(request)
        }

        if (ignorePath[1] == request.url.encodedPath && ignoreMethod[1] == request.method) {
            return chain.proceed(request)
        }

        if (LocalDateTime.now().isAfter(parseToKoreaDateTime(authTokenDataSource.getRefreshTokenExp()))) throw NeedLoginException()
        if (LocalDateTime.now().isAfter(parseToKoreaDateTime(authTokenDataSource.getAccessTokenExp()))) {
            val currentRefreshToken = authTokenDataSource.getRefreshToken()
            val newRequest = chain.request().newBuilder()
                .url(BuildConfig.BASE_URL+"auth")
                .addHeader("refreshToken", "Bearer $currentRefreshToken")
                .method("PATCH", "".toRequestBody(null))
                .build()
            val refreshResponse = chain.proceed(newRequest)
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

        // 이 부분이 기존 요청에 token header를 붙여 return 하는 곳
        val accessToken = authTokenDataSource.getAccessToken()
        return chain.proceed(
            request.newBuilder()
                .addHeader("Authorization", "Bearer $accessToken")
                .build()
        )
    }

    private fun parseToKoreaDateTime(refreshTokenExp: String): LocalDateTime? {
        val parsePattern = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH-mm-ss")
        val koreaZone = ZoneId.of("Asia/Seoul")
        val expireDate = LocalDateTime.parse(refreshTokenExp, parsePattern)

        return expireDate.atZone(ZoneId.of("UTC")).withZoneSameInstant(koreaZone).toLocalDateTime()
    }

    private fun String.deleteDot(): String {
        return this.substring(1, this.length-1)
    }
}