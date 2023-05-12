package com.goms.domain.usecase.admin

import com.goms.domain.data.council.ModifyRoleRequestData
import com.goms.domain.repository.CouncilRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class ModifyRoleUseCase @Inject constructor(
    private val councilRepository: CouncilRepository
) {
    suspend operator fun invoke(body: ModifyRoleRequestData): Flow<Response<Unit>> {
        return councilRepository.modifyRole(body)
    }
}