package com.example.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.data.signin.response.SignInResponseData
import com.example.domain.usecase.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
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