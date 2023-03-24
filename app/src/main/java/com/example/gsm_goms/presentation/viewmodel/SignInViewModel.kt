package com.example.gsm_goms.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gsm_goms.domain.data.signin.response.SignInResponseData
import com.example.gsm_goms.domain.usecase.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
): ViewModel() {
    private val _signIn = MutableStateFlow<SignInResponseData?>(null)
    val signIn: StateFlow<SignInResponseData?> = _signIn

    fun signInLogic(code: String) = viewModelScope.launch {
        signInUseCase(code).onStart { 
            // loading start logic
        }.onCompletion { 
            // loading complete logic
        }.catch { error ->
            Log.e("TAG", "signInLogic: ${error.printStackTrace()}", error.cause)
        }.collect { response ->
            _signIn.value = response
        }
    }
}