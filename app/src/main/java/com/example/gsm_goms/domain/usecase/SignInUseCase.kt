package com.example.gsm_goms.domain.usecase

import com.example.gsm_goms.domain.data.signin.response.SignInResponseData
import com.example.gsm_goms.domain.repository.SignInRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val signInRepository: SignInRepository
) {
    suspend operator fun invoke(
        code: String
    ): Flow<SignInResponseData> {
        return signInRepository.signIn(code)
    }
}