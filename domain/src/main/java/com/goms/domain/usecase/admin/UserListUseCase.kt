package com.goms.domain.usecase.admin

import com.goms.domain.data.council.response.UserInfoResponseData
import com.goms.domain.repository.CouncilRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserListUseCase @Inject constructor(
    private val councilRepository: CouncilRepository
) {
    suspend operator fun invoke(): Flow<List<UserInfoResponseData>> {
        return councilRepository.getUserList()
    }
}