package com.goms.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goms.domain.data.council.request.ModifyRoleRequestData
import com.goms.domain.data.council.response.MakeQrCodeResponseData
import com.goms.domain.data.council.response.SearchStudentResponseData
import com.goms.domain.data.council.response.UserListResponseData
import com.goms.domain.exception.NotCouncilException
import com.goms.domain.exception.OtherException
import com.goms.domain.exception.ServerException
import com.goms.domain.exception.UserNotFoundException
import com.goms.domain.usecase.admin.MakeQrCodeUseCase
import com.goms.domain.usecase.admin.ModifyRoleUseCase
import com.goms.domain.usecase.admin.SearchStudentUseCase
import com.goms.domain.usecase.admin.SetBlackListUseCase
import com.goms.domain.usecase.admin.UserListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CouncilViewModel @Inject constructor(
    private val userListUseCase: UserListUseCase,
    private val modifyRoleUseCase: ModifyRoleUseCase,
    private val setBlackListUseCase: SetBlackListUseCase,
    private val searchStudentUseCase: SearchStudentUseCase,
    private val makeQrCodeUseCase: MakeQrCodeUseCase
): ViewModel() {
    private val _userList: MutableStateFlow<List<UserListResponseData>?> = MutableStateFlow(null)
    val userList: StateFlow<List<UserListResponseData>?> = _userList

    private val _modifyRole: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val modifyRole: StateFlow<Boolean> = _modifyRole

    private val _setBlackList: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val setBlackList: StateFlow<Boolean> = _setBlackList

    private val _searchStudent: MutableStateFlow<List<SearchStudentResponseData>?> = MutableStateFlow(null)
    val searchStudent: StateFlow<List<SearchStudentResponseData>?> = _searchStudent

    private val _makeQr: MutableStateFlow<MakeQrCodeResponseData?> = MutableStateFlow(null)
    val makeQr: StateFlow<MakeQrCodeResponseData?> = _makeQr

    fun getUserList() {
        viewModelScope.launch {
            userListUseCase().catch {
                if (it is HttpException) {
                    when (it.code()) {
                        403 -> throw NotCouncilException("학생회 계정이 아닙니다.")
                        500 -> throw ServerException("서버 에러")
                    }
                } else throw OtherException(it.message)
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
                } else throw OtherException(it.message)
            }.collect {
                _modifyRole.value = true
            }
        }
    }

    fun setBlackList(accountIdx: UUID) {
        viewModelScope.launch {
            setBlackListUseCase(accountIdx).catch {
                if (it is HttpException) {
                    when (it.code()) {
                        403 -> throw NotCouncilException("학생회 계정이 아닙니다.")
                        404 -> throw UserNotFoundException("계정을 찾을 수 없습니다.")
                        500 -> throw ServerException("서버 에러")
                    }
                } else throw OtherException(it.message)
            }.collect {
                _setBlackList.value = true
            }
        }
    }

    fun searchStudent(
        grade: Int?,
        classNum: Int?,
        name: String?,
        isBlackList: Boolean?,
        authority: String?
    ) {
        viewModelScope.launch {
            searchStudentUseCase(grade, classNum, name, isBlackList, authority).catch {
                if (it is HttpException) {
                    when (it.code()) {
                        403 -> throw NotCouncilException("학생회 계정이 아닙니다.")
                        500 -> throw ServerException("서버 에러")
                    }
                } else throw OtherException(it.message)
            }.collect {
                _searchStudent.value = it
            }
        }
    }

    fun makeQrCode() {
        viewModelScope.launch {
            makeQrCodeUseCase().catch {
                if (it is HttpException) {
                    when (it.code()) {
                        403 -> throw NotCouncilException("학생회 계정이 아닙니다.")
                        500 -> throw ServerException("서버 에러")
                    }
                } else throw OtherException(it.message)
            }.collect {
                _makeQr.value = it
            }
        }
    }
}