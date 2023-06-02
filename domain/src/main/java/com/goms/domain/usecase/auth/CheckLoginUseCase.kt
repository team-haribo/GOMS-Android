package com.goms.domain.usecase.auth

import com.goms.domain.repository.AuthRepository
import javax.inject.Inject

class CheckLoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke() = kotlin.runCatching {
        authRepository.checkLoginStatus()
    }
}