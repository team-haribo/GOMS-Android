package com.example.data.repository

import com.example.data.mapper.UserMapper
import com.example.data.source.SignInDataSource
import com.example.domain.data.signin.response.SignInResponseData
import com.example.domain.repository.SignInRepository
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