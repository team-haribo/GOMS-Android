package com.goms.domain.usecase.admin

import com.goms.domain.data.council.response.UserInfoResponseData
import com.goms.domain.repository.CouncilRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchStudentUseCase @Inject constructor(
    private val councilRepository: CouncilRepository
) {
    suspend operator fun invoke(
        grade: Int?,
        classNum: Int?,
        name: String?,
        isBlackList: Boolean?,
        authority: String?
    ): Flow<List<UserInfoResponseData>> {
        return councilRepository.searchStudent(grade, classNum, name, isBlackList, authority)
    }
}