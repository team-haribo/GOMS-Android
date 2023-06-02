package com.goms.domain.usecase.outing

import com.goms.domain.repository.OutingRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import java.util.UUID
import javax.inject.Inject

class OutingUseCase @Inject constructor(
    private val outingRepository: OutingRepository
) {
    suspend operator fun invoke(outingUUID: UUID): Flow<Response<Unit>> {
        return outingRepository.outing(outingUUID)
    }
}