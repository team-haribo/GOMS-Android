package com.goms.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goms.domain.data.signin.response.SignInResponseData
import com.goms.domain.exception.NotRequestParamException
import com.goms.domain.exception.OtherException
import com.goms.domain.exception.ServerException
import com.goms.domain.usecase.auth.SetTokenUseCase
import com.goms.domain.usecase.auth.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val setTokenUseCase: SetTokenUseCase
): ViewModel() {
    private val _signIn = MutableStateFlow<SignInResponseData?>(null)
    val signIn: StateFlow<SignInResponseData?> = _signIn

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun signInLogic(code: String) = viewModelScope.launch {
        signInUseCase(code).onStart {
            _isLoading.value = true
        }.onCompletion {
            _isLoading.value = false
        }.catch { error ->
            if (error is HttpException) {
                val message = error.message
                throw when(error.code()) {
                    400 -> NotRequestParamException(message = message)
                    500 -> ServerException(message = message)
                    else -> OtherException(
                        code = error.code(),
                        message = message
                    )
                }
            } else Log.e("TAG", "signInLogic: ${error.printStackTrace()}", error.cause)
        }.collect { response ->
            setTokenUseCase(
                accessToken = response.accessToken,
                refreshToken = response.refreshToken,
                accessTokenExp = response.accessTokenExp.toString(),
                refreshTokenExp = response.refreshTokenExp.toString()
            )
            _signIn.value = response
        }
    }
}