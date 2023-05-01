package com.goms.data.repository

import android.util.Log
import com.goms.data.datasource.auth.AuthDataSource
import com.goms.data.datasource.token.ManageTokenDataSource
import com.goms.data.mapper.AuthMapper
import com.goms.domain.data.signin.response.SignInResponseData
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
    private val manageTokenDataSource: ManageTokenDataSource
) : AuthRepository {
    override suspend fun signIn(code: String): Flow<SignInResponseData> {
        return flow {
            authDataSource.signIn(code).collect {
                emit(AuthMapper.signInResponseToData(it))
            }
        }
    }

    override suspend fun checkLoginStatus() {
        val accessToken = manageTokenDataSource.getAccessToken()
        if (accessToken.isBlank()) throw NeedLoginException()

        val accessTokenExp = manageTokenDataSource.getAccessTokenExp()
        val parsePattern = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH-mm-ss")
        val koreaZone = ZoneId.of("Asia/Seoul")
        val expireDate = LocalDateTime.parse(accessTokenExp, parsePattern)
        val koreaTime = expireDate.atZone(ZoneId.of("UTC")).withZoneSameInstant(koreaZone).toLocalDateTime()

        // 현재 시간이 만료 시간보다 미래인가요?(만료시간보다 시간이 지남)
        if (LocalDateTime.now().isAfter(koreaTime)) throw NeedLoginException()
        val refreshToken = manageTokenDataSource.getRefreshToken()
        authDataSource.refreshToken("Bearer $refreshToken").catch {
            throw NeedLoginException()
        }.collect { result ->
            Log.d("TAG", "checkLoginStatus: $result")
            manageTokenDataSource.setToken(
                accessToken = result.accessToken,
                refreshToken = result.refreshToken,
                accessTokenExp = result.accessTokenExpiredAt.toString()
            )
        }
    }

    override fun getRefreshToken(): String = manageTokenDataSource.getRefreshToken()
    override fun getAccessToken(): String = manageTokenDataSource.getAccessToken()
    override fun getAccessTokenExp(): String = manageTokenDataSource.getAccessTokenExp()

    override suspend fun setToken(
        accessToken: String,
        refreshToken: String,
        accessTokenExp: String
    ) {
        manageTokenDataSource.setToken(accessToken, refreshToken, accessTokenExp)
    }
}