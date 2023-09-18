package com.goms.presentation.viewmodel

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goms.domain.data.late.LateUserResponseData
import com.goms.domain.exception.FailAccessTokenException
import com.goms.domain.exception.InternetConnectException
import com.goms.domain.exception.OtherException
import com.goms.domain.exception.ServerException
import com.goms.domain.usecase.late.LateUseCase
import com.goms.presentation.utils.apiErrorHandling
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class LateViewModel @Inject constructor(
    private val lateUseCase: LateUseCase
): ViewModel() {
    private val _lateRanking: MutableStateFlow<List<LateUserResponseData>?> = MutableStateFlow(null)
    val lateRanking: StateFlow<List<LateUserResponseData>?> = _lateRanking

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun getLateRanking(activity: AppCompatActivity) {
        viewModelScope.launch {
            apiErrorHandling(activity = activity) {
                lateUseCase().onStart {
                    _isLoading.value = true
                }.onCompletion {
                    _isLoading.value = false
                }.catch {
                    when (it) {
                        is HttpException -> {
                            when(it.code()) {
                                401 -> throw FailAccessTokenException("access token이 유효하지 않습니다")
                                500 -> throw ServerException("서버 에러")
                            }
                        }
                        is UnknownHostException, is SocketTimeoutException -> throw InternetConnectException()
                        else -> throw OtherException(it.message)
                    }
                }.collect {
                    _lateRanking.value = it
                }
            }
        }
    }
}