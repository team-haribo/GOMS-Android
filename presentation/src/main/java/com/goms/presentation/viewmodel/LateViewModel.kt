package com.goms.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.goms.domain.data.profile.ProfileResponseData
import com.goms.domain.exception.FailAccessTokenException
import com.goms.domain.exception.OtherException
import com.goms.domain.exception.ServerException
import com.goms.domain.usecase.late.LateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class LateViewModel @Inject constructor(
    private val lateUseCase: LateUseCase
): ViewModel() {
    private val _lateRanking: MutableStateFlow<List<ProfileResponseData>?> = MutableStateFlow(null)
    val lateRanking: StateFlow<List<ProfileResponseData>?> = _lateRanking

    suspend fun getLateRanking() {
        lateUseCase().catch {
            if (it is HttpException) {
                when(it.code()) {
                    401 -> throw FailAccessTokenException("access token이 유효하지 않습니다")
                    500 -> throw ServerException("서버 에러")
                }
            } else throw OtherException(it.message)
        }.collect {
            _lateRanking.value = it
        }
    }
}