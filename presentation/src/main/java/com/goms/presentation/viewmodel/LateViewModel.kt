package com.goms.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.goms.domain.data.profile.ProfileResponseData
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
                    401 -> Log.d("TAG", "getLfiateRanking: 토큰 이슈")
                    404 -> _lateRanking.value = emptyList()
                    500 -> Log.d("TAG", "getLateRanking: server error")
                }
            } else Log.d("TAG", "getLateRanking: $it")
        }.collect {
            _lateRanking.value = it
        }
    }
}