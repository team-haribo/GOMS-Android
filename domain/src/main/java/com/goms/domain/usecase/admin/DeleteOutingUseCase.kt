package com.goms.domain.usecase.admin

import com.goms.domain.repository.CouncilRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import java.util.UUID
import javax.inject.Inject

class DeleteOutingUseCase @Inject constructor(
    private val councilRepository: CouncilRepository
) {
    suspend operator fun invoke(accountIdx: UUID): Flow<Response<Unit>> {
        return councilRepository.deleteOuting(accountIdx)
    }
}