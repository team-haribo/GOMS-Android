package com.goms.domain.usecase.profile

import com.goms.domain.data.profile.response.ProfileResponseData
import com.goms.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke()
    : Flow<ProfileResponseData> {
        return profileRepository.getProfile()
    }
}