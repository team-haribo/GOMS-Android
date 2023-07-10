package com.goms.domain.usecase.outing

import com.goms.domain.data.user.UserResponseData
import com.goms.domain.repository.OutingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OutingStudentSearchUseCase @Inject constructor(
    private val outingRepository: OutingRepository
){
    suspend operator fun invoke(name: String): Flow<List<UserResponseData>>{
        return outingRepository.searchOutingStudent(name)
    }
}