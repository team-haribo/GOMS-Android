package com.goms.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goms.domain.exception.FailAccessTokenException
import com.goms.domain.exception.OtherException
import com.goms.domain.exception.ServerException
import com.goms.domain.usecase.notification.SetNotificationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val setNotificationUseCase: SetNotificationUseCase
): ViewModel() {
    private val _setNotification = MutableStateFlow<Response<Unit>?>(null)
    val setNotification: StateFlow<Response<Unit>?> = _setNotification

    fun setNotification(deviceToken: String) {
        viewModelScope.launch {
            setNotificationUseCase(deviceToken).catch {
                if (it is HttpException) {
                    when (it.code()) {
                        401 -> throw FailAccessTokenException("access token이 유효하지 않습니다")
                        500 -> throw ServerException("서버 에러")
                    }
                } else throw OtherException(it.message)
            }.collect {
                _setNotification.value = it
            }
        }
    }
}