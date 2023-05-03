package com.goms.domain.usecase.outing

import com.goms.domain.data.profile.response.ProfileResponseData
import com.goms.domain.repository.OutingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OutingListUseCase @Inject constructor(
    private val outingRepository: OutingRepository
) {
    suspend operator fun invoke(): Flow<List<ProfileResponseData>>{
        return outingRepository.getOutingList()
    }
}