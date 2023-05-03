package com.goms.domain.usecase.outing

import com.goms.domain.repository.OutingRepository
import javax.inject.Inject

class OutingUseCase @Inject constructor(
    private val outingRepository: OutingRepository
) {
    suspend operator fun invoke() = kotlin.runCatching {
        outingRepository.outing()
    }
}