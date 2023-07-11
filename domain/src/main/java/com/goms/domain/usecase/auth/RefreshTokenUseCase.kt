package com.goms.domain.usecase.auth

import com.goms.domain.data.auth.response.RefreshTokenResponseData
import com.goms.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RefreshTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(refreshToken: String): Flow<RefreshTokenResponseData> = authRepository.refreshToken(refreshToken)
}