package com.goms.domain.usecase.admin

import com.goms.domain.data.council.response.MakeQrCodeResponseData
import com.goms.domain.repository.CouncilRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MakeQrCodeUseCase @Inject constructor(
    private val councilRepository: CouncilRepository
) {
    suspend operator fun invoke(): Flow<MakeQrCodeResponseData> {
        return councilRepository.makeQrCode()
    }
}