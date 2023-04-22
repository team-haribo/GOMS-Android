package com.goms.data.source

import com.goms.data.api.SignInService
import com.goms.data.model.signin.request.SignInRequest
import com.goms.data.model.signin.response.SignInResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignInDataSourceImpl @Inject constructor(
    private val signInService: SignInService
): SignInDataSource {
    override suspend fun signIn(code: String): Flow<SignInResponse> {
        return flow {
            emit(signInService.signIn(SignInRequest(code)))
        }
    }
}