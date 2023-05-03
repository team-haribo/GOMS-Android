package com.goms.domain.usecase.auth

import com.goms.domain.data.auth.response.SignInResponseData
import com.goms.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        code: String
    ): Flow<SignInResponseData> {
        return authRepository.signIn(code)
    }
}