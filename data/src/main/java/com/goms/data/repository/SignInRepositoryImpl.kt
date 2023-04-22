package com.goms.data.repository

import com.goms.data.mapper.UserMapper
import com.goms.data.source.SignInDataSource
import com.goms.domain.data.signin.response.SignInResponseData
import com.goms.domain.repository.SignInRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignInRepositoryImpl @Inject constructor(
    private val signInDataSource: SignInDataSource
): SignInRepository {
    override suspend fun signIn(code: String): Flow<SignInResponseData> {
        return flow {
            signInDataSource.signIn(code).collect {
                emit(UserMapper.signInResponseToData(it))
            }
        }
    }
}