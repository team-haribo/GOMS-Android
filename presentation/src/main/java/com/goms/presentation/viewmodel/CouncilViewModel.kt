package com.goms.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goms.domain.data.council.ModifyRoleRequestData
import com.goms.domain.data.user.UserResponseData
import com.goms.domain.exception.NotCouncilException
import com.goms.domain.exception.ServerException
import com.goms.domain.exception.UserNotFoundException
import com.goms.domain.usecase.admin.ModifyRoleUseCase
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
    private val userListUseCase: UserListUseCase,
    private val modifyRoleUseCase: ModifyRoleUseCase
): ViewModel() {
    private val _userList: MutableStateFlow<List<UserResponseData>?> = MutableStateFlow(null)
    val userList: StateFlow<List<UserResponseData>?> = _userList

    private val _modifyRole: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val modifyRole: StateFlow<Boolean> = _modifyRole

    fun getUserList() {
        viewModelScope.launch {
            userListUseCase().catch {
                if (it is HttpException) {
                    when (it.code()) {
                        403 -> throw NotCouncilException("학생회 계정이 아닙니다.")
                        500 -> throw ServerException("서버 에러")
                    }
                } else Log.d("TAG", "getUserList error: $it")
            }.collect { list ->
                _userList.value = list
            }
        }
    }

    fun modifyRole(body: ModifyRoleRequestData) {
        viewModelScope.launch {
            modifyRoleUseCase(body).catch {
                if (it is HttpException) {
                    when (it.code()) {
                        403 -> throw NotCouncilException("학생회 계정이 아닙니다.")
                        404 -> throw UserNotFoundException("계정을 찾을 수 없습니다.")
                        500 -> throw ServerException("서버 에러")
                    }
                } else Log.d("TAG", "modifyRole error: $it")
            }.collect {
                _modifyRole.value = true
            }
        }
    }
}