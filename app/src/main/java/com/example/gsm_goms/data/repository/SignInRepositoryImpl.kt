package com.example.gsm_goms.data.repository

import com.example.gsm_goms.data.mapper.UserMapper
import com.example.gsm_goms.data.model.signin.request.SignInRequest
import com.example.gsm_goms.data.source.signin.SignInDataSource
import com.example.gsm_goms.domain.data.signin.request.SignInRequestData
import com.example.gsm_goms.domain.data.signin.response.SignInResponseData
import com.example.gsm_goms.domain.repository.SignInRepository
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