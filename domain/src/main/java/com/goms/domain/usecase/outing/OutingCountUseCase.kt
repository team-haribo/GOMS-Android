package com.goms.domain.usecase.outing

import com.goms.domain.data.outing.OutingCountResponseData
import com.goms.domain.repository.OutingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OutingCountUseCase @Inject constructor(
    private val outingRepository: OutingRepository
) {
    suspend operator fun invoke(): Flow<OutingCountResponseData> {
        return outingRepository.outingCount()
    }
}