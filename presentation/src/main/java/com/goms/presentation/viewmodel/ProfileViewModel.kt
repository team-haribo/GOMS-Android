package com.goms.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goms.domain.data.profile.ProfileResponseData
import com.goms.domain.exception.FailAccessTokenException
import com.goms.domain.exception.OtherException
import com.goms.domain.exception.ServerException
import com.goms.domain.usecase.profile.ProfileUseCase
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
class ProfileViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase
): ViewModel() {
    private val _profile: MutableStateFlow<ProfileResponseData?> = MutableStateFlow(null)
    val profile: StateFlow<ProfileResponseData?> = _profile

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun getProfileLogic() {
        viewModelScope.launch {
            profileUseCase().onStart {
                _isLoading.value = true
            }.onCompletion {
                _isLoading.value = false
            }.catch {
                if (it is HttpException) {
                    when (it.code()) {
                        401 -> throw FailAccessTokenException("access token이 유효하지 않습니다")
                        500 -> throw ServerException("서버 에러")
                    }
                } else throw OtherException(it.message)
            }.collect {
                _profile.value = it
            }
        }
    }
}