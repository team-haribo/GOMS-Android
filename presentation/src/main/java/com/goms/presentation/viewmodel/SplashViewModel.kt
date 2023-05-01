package com.goms.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goms.domain.usecase.auth.CheckLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val checkLoginUseCase: CheckLoginUseCase
): ViewModel() {
    private val _isLogin = MutableLiveData<Boolean?>(null)
    val isLogin: LiveData<Boolean?> = _isLogin

    fun checkIsLogin() = viewModelScope.launch {
        checkLoginUseCase().onSuccess {
            _isLogin.value = true
        }.onFailure {
            _isLogin.value = false
        }
    }
}