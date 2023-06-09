package com.goms.domain.usecase.outing

import com.goms.domain.data.user.UserResponseData
import com.goms.domain.repository.OutingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OutingListUseCase @Inject constructor(
    private val outingRepository: OutingRepository
) {
    suspend operator fun invoke(): Flow<List<UserResponseData>>{
        return outingRepository.getOutingList()
    }
}