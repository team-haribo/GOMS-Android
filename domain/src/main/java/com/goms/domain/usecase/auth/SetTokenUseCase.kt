package com.goms.domain.usecase.auth

import com.goms.domain.repository.AuthRepository
import javax.inject.Inject

class SetTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        accessToken: String,
        refreshToken: String,
        accessTokenExp: String
    ) {
        authRepository.setToken(accessToken, refreshToken, accessTokenExp)
    }
}