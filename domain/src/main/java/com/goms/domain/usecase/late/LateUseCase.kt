package com.goms.domain.usecase.late

import com.goms.domain.data.late.LateUserResponseData
import com.goms.domain.repository.LateRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LateUseCase @Inject constructor(
    private val lateRepository: LateRepository
) {
    suspend operator fun invoke(): Flow<List<LateUserResponseData>> {
        return lateRepository.getLateRank()
    }
}