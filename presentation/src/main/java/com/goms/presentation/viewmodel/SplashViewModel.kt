package com.goms.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goms.domain.data.auth.response.RefreshTokenResponseData
import com.goms.domain.exception.NotRequestParamException
import com.goms.domain.exception.OtherException
import com.goms.domain.exception.ServerException
import com.goms.domain.exception.UserNotFoundException
import com.goms.domain.usecase.auth.CheckLoginUseCase
import com.goms.domain.usecase.auth.RefreshTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val checkLoginUseCase: CheckLoginUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase
): ViewModel() {
    private val _isLogin = MutableLiveData<Boolean?>(null)
    val isLogin: LiveData<Boolean?> = _isLogin

    private val _token = MutableStateFlow<RefreshTokenResponseData?>(null)
    val token: StateFlow<RefreshTokenResponseData?> = _token

    fun checkIsLogin() = viewModelScope.launch {
        checkLoginUseCase().onSuccess {
            _isLogin.value = true
        }.onFailure {
            _isLogin.value = false
        }
    }

    fun refreshToken(refreshToken: String) {
        viewModelScope.launch {
            refreshTokenUseCase("Bearer $refreshToken").catch {
                if (it is HttpException) {
                    when (it.code()) {
                        400 -> throw NotRequestParamException("토큰 요청이 올바르지 않습니다.")
                        404 -> throw UserNotFoundException("계정을 찾을 수 없습니다.")
                        500 -> throw ServerException("서버 에러")
                    }
                } else throw OtherException(it.message)
            }.collect {
                _token.value = it
            }
        }
    }
}