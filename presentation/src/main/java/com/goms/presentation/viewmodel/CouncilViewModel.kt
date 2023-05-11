package com.goms.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goms.domain.data.user.UserResponseData
import com.goms.domain.exception.ServerException
import com.goms.domain.usecase.admin.UserListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class CouncilViewModel @Inject constructor(
    private val userListUseCase: UserListUseCase
): ViewModel() {
    private val _userList: MutableStateFlow<List<UserResponseData>?> = MutableStateFlow(null)
    val userList: StateFlow<List<UserResponseData>?> = _userList

    fun getUserList() {
        viewModelScope.launch {
            userListUseCase().catch {
                if (it is HttpException) {
                    when (it.code()) {
                        403 -> Log.d("TAG", "getUserList: 학생회 계정이 아님")
                        500 -> throw ServerException("서버 에러")
                    }
                } else Log.d("TAG", "getUserList error: $it")
            }.collect { list ->
                _userList.value = list
            }
        }
    }
}