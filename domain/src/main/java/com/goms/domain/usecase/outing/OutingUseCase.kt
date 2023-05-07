package com.goms.domain.usecase.outing

import com.goms.domain.repository.OutingRepository
import java.util.UUID
import javax.inject.Inject

class OutingUseCase @Inject constructor(
    private val outingRepository: OutingRepository
) {
    suspend operator fun invoke(outingUUID: UUID) = kotlin.runCatching {
        outingRepository.outing(outingUUID)
    }
}