package com.goms.data.repository

import com.goms.data.datasource.auth.AuthDataSource
import com.goms.data.datasource.token.AuthTokenDataSource
import com.goms.data.mapper.AuthMapper
import com.goms.domain.data.auth.response.SignInResponseData
import com.goms.domain.exception.NeedLoginException
import com.goms.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val authTokenDataSource: AuthTokenDataSource
) : AuthRepository {
    override suspend fun signIn(code: String): Flow<SignInResponseData> {
        return flow {
            authDataSource.signIn(code).collect {
                emit(AuthMapper.signInResponseToData(it))
            }
        }
    }

    override suspend fun checkLoginStatus() {
        val accessToken = authTokenDataSource.getAccessToken()
        if (accessToken.isBlank()) throw NeedLoginException()

        val refreshTokenExp = authTokenDataSource.getRefreshTokenExp()
        val parsePattern = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH-mm-ss")
        val koreaZone = ZoneId.of("Asia/Seoul")
        val expireDate = LocalDateTime.parse(refreshTokenExp, parsePattern)
        val koreaTime = expireDate.atZone(ZoneId.of("UTC")).withZoneSameInstant(koreaZone).toLocalDateTime()

        // 현재 시간이 만료 시간보다 미래인가요?(만료시간보다 시간이 지남)
        if (LocalDateTime.now().isAfter(koreaTime)) throw NeedLoginException()
        val refreshToken = authTokenDataSource.getRefreshToken()
        authDataSource.refreshToken("Bearer $refreshToken").catch {
            throw NeedLoginException()
        }.collect { result ->
            authTokenDataSource.setToken(
                accessToken = result.accessToken,
                refreshToken = result.refreshToken,
                accessTokenExp = result.accessTokenExpiredAt.toString(),
                refreshTokenExp = result.refreshTokenExpiredAt.toString()
            )
        }
    }

    override fun getRefreshToken(): String = authTokenDataSource.getRefreshToken()
    override fun getAccessToken(): String = authTokenDataSource.getAccessToken()
    override fun getAccessTokenExp(): String = authTokenDataSource.getAccessTokenExp()
    override fun getRefreshTokenExp(): String = authTokenDataSource.getRefreshTokenExp()

    override suspend fun setToken(
        accessToken: String,
        refreshToken: String,
        accessTokenExp: String,
        refreshTokenExp: String
    ) {
        authTokenDataSource.setToken(accessToken, refreshToken, accessTokenExp, refreshTokenExp)
    }
}