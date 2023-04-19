package com.example.domain.usecase

import com.example.domain.data.signin.response.SignInResponseData
import com.example.domain.repository.SignInRepository
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